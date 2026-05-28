package com.example.kpmapp_with_di.data.reminders

import com.example.kpmapp_with_di.data.common.db.LocalDataSource

internal class RemindersRepository(
    private val localDataSource: LocalDataSource
) {
    val reminders: List<Reminder>
        get() = localDataSource.getAllTasks().map { it.map() }

    fun createReminder(title: String) {
        localDataSource.insertTask(title)
    }

    fun markReminder(id: Long, isCompleted: Boolean) {
        if (isCompleted) {
            localDataSource.markTaskCompleted(id)
        } else {
            localDataSource.markTaskPending(id)
        }
    }
}