package com.melissa.todoapp.addtasks.ui.models

data class TaskModel(
    var id: Long = System.currentTimeMillis(),
    val task: String,
    var selected: Boolean = false,
) {
}