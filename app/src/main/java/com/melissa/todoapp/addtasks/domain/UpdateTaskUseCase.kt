package com.melissa.todoapp.addtasks.domain

import com.melissa.todoapp.addtasks.data.TaskRepository
import com.melissa.todoapp.addtasks.ui.models.TaskModel
import javax.inject.Inject

class UpdateTaskUseCase @Inject constructor(private val taskRepository: TaskRepository) {

    suspend operator fun invoke(taskModel: TaskModel){
        taskRepository.update(taskModel)
    }
}