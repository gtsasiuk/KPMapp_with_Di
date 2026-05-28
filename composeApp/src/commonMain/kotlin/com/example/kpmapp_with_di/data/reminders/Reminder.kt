package com.example.kpmapp_with_di.data.reminders

import com.example.kpmapp_with_di.Task

internal data class Reminder(
    val id: Long,
    val title: String,
    val isCompleted: Boolean = false
)

internal fun Task.map(): Reminder {
    return Reminder(
        id = id,
        title = task_desc,
        isCompleted = is_completed == 1L
    )
}