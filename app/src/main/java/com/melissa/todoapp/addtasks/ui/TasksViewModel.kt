package com.melissa.todoapp.addtasks.ui

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melissa.todoapp.addtasks.domain.AddTaskUseCase
import com.melissa.todoapp.addtasks.domain.DeleteTaskUseCase
import com.melissa.todoapp.addtasks.domain.GetTasksUseCase
import com.melissa.todoapp.addtasks.domain.UpdateTaskUseCase
import com.melissa.todoapp.addtasks.ui.TasksUIState.Success
import com.melissa.todoapp.addtasks.ui.models.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TasksViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    getTasksUseCase: GetTasksUseCase
):ViewModel() {
    // Empezamos creando un Stateflow del TasksUIState.
    //Para eso queremos el resultado del caso de uso gettasks q devuelve el flow continuo.
    // Cada vez q actualiza los datos, retorno succcess.
    // StateIn: convierte un flow en un stateflow.
    // Mientras no hay datos, muestra el estado Loading
    val uiState:StateFlow<TasksUIState> = getTasksUseCase().map ( :: Success )
        .catch { TasksUIState.Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),TasksUIState.Loading)

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialod:LiveData<Boolean> = _showDialog

    fun onDialogClose(){
        _showDialog.value = false
    }

    fun onTasksCreated(task:String){
        _showDialog.value = false
        Log.i("onTaskCreated",task)

        //Ahora q estamos guardando en base de datos y leyendo directamente, no necesitamos esto
        //_tasks.add(TaskModel(task = task))

        viewModelScope.launch {
            addTaskUseCase(taskModel = TaskModel(
                 task = task
            ))
        }
    }

    fun onShowDialogClick(){
        _showDialog.value = true
    }


    fun onCheckBoxSelected(taskModel: TaskModel) {

        //tiene q crearse un nuevo objeto que reemplace al anterior , mediante un "copywith"
        viewModelScope.launch {
            updateTaskUseCase(taskModel.copy(selected = !taskModel.selected))
        }

    }

    fun onItemRemove(taskModel: TaskModel) {

        viewModelScope.launch {
            deleteTaskUseCase(taskModel)
        }

    }
}