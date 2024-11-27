package com.melissa.todoapp.addtasks.domain

import com.melissa.todoapp.addtasks.data.TaskRepository
import com.melissa.todoapp.addtasks.ui.models.TaskModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(private val taskRepository: TaskRepository){
    operator fun invoke(): Flow<List<TaskModel>>{
    return taskRepository.tasks
    }
}