package com.melissa.todoapp.addtasks.ui.models

data class TaskModel(
    var id: Int = System.currentTimeMillis().hashCode(),
    val task: String,
    var selected: Boolean = false,
) {
}