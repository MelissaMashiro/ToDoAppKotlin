package com.melissa.todoapp.addtasks.data

import com.melissa.todoapp.addtasks.ui.models.TaskModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(private val taskDao: TaskDao) {

    val tasks: Flow<List<TaskModel>> =
        taskDao.getTasks().map { items -> items.map { TaskModel(it.id, it.task, it.selected) } }

    suspend fun add(taskModel: TaskModel) {
        taskDao.addTask(taskModel.toEntity())
    }

    suspend fun update(taskModel: TaskModel) {
        taskDao.updateTask(taskModel.toEntity())
    }

    suspend fun delete(taskModel: TaskModel) {
        taskDao.deleteTask(taskModel.toEntity())
    }

    //opcional: Funciond e extension
 fun TaskModel.toEntity():TaskEntity{
     return TaskEntity(this.id,this.task,this.selected)
 }
}