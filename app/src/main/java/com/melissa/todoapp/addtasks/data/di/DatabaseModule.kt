package com.melissa.todoapp.addtasks.data.di

import android.content.Context
import androidx.room.Room
import com.melissa.todoapp.addtasks.data.TaskDao
import com.melissa.todoapp.addtasks.data.TodoLocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideTaskDao(todoLocalDatabase: TodoLocalDatabase):TaskDao{
        return todoLocalDatabase.taskDao()
    }

    @Provides
    @Singleton
    fun provideTodoDatabase(@ApplicationContext appContext: Context): TodoLocalDatabase{
        return Room.databaseBuilder(appContext,TodoLocalDatabase::class.java,"TaskDatabase").build()
    }
}