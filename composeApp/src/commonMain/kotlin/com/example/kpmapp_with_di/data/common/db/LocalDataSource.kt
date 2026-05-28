package com.example.kpmapp_with_di.data.common.db

import com.example.kpmapp_with_di.Task

interface LocalDataSource {
    fun insertTask(description: String)
    fun getAllTasks(): List<Task>
    fun markTaskCompleted(id: Long)
    fun markTaskPending(id: Long)
    fun deleteTask(id: Long)
}