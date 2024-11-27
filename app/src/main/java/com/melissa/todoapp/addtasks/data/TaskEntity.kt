package com.melissa.todoapp.addtasks.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskEntity(
    @PrimaryKey
    var id: Int,
    val task: String,
    var selected: Boolean = false,
) {
}