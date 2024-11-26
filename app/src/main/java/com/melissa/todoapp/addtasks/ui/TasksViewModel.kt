package com.melissa.todoapp.addtasks.ui

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.melissa.todoapp.addtasks.ui.models.TaskModel
import javax.inject.Inject


class TasksViewModel @Inject constructor():ViewModel() {

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialod:LiveData<Boolean> = _showDialog

    //para esta lista actualizable de tasks NO se usará un LIVEDATA
    // POR QUE? Porque livedata no funciona bien con los listados que se actualizan .
    private val _tasks = mutableStateListOf<TaskModel>()
    val tasks:List<TaskModel> = _tasks

    fun onDialogClose(){
        _showDialog.value = false
    }

    fun onTasksCreated(task:String){
        _showDialog.value = false
        Log.i("onTaskCreated",task)
        _tasks.add(TaskModel(task = task))
    }

    fun onShowDialogClick(){
        _showDialog.value = true
    }


    fun onCheckBoxSelected(taskModel: TaskModel) {
      val index = _tasks.indexOf(taskModel)
        _tasks[index] = _tasks[index].let {
            //tiene q crearse un nuevo objeto mediante "copywith" ya que la vista no reconoce si hubo un cambio dentro del objeto
         it.copy(selected = !it.selected)


            //NO FUNCIONA POR LA EXPLICACION ANTERIOR.
          /* it.apply {
                selected = !it.selected
            }
            */


        }
    }

    fun onItemRemove(taskModel: TaskModel) {
        //NO FUNCIONA CUANDO SE HA SELECCIONADO EL CHECKBOX : Porque se esta usando un copywith al activar el checkbox, por lo que el item que se ve no es en memoria el mismo q esta guardado en la lista
       // _tasks.remove(taskModel)
        val task = _tasks.find { it.id == taskModel.id }
        _tasks.remove(task)
    }
}