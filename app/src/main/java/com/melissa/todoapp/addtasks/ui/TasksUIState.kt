package com.melissa.todoapp.addtasks.ui

import com.melissa.todoapp.addtasks.ui.models.TaskModel

sealed interface TasksUIState {
    object Loading:TasksUIState
    data class Error(val throwable:Throwable):TasksUIState
    data class Success(val tasks:List<TaskModel>) : TasksUIState
}