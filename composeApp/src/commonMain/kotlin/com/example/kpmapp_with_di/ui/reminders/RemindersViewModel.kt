package com.example.kpmapp_with_di.ui.reminders

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.example.kpmapp_with_di.data.reminders.Reminder
import com.example.kpmapp_with_di.data.reminders.RemindersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@Stable
internal class RemindersViewModel(
    private val repository: RemindersRepository
) : ViewModel() {
    private val logger = Logger.withTag("RemindersViewModel")

    private val _state = MutableStateFlow<List<Reminder>>(emptyList())
    val state: StateFlow<List<Reminder>> = _state.asStateFlow()

    init {
        logger.i { "RemindersViewModel initialized" }
        loadReminders()
    }

    private fun loadReminders() {
        logger.d { "Loading reminders..." }
        viewModelScope.launch {
            try {
                _state.value = repository.reminders
                logger.i { "Loaded ${_state.value.size} reminders" }
            } catch (e: Exception) {
                logger.e(e) { "Failed to load reminders" }
            }
        }
    }

    fun createReminder(title: String) {
        val trimmed = title.trim()
        if (trimmed.isNotEmpty()) {
            logger.d { "Creating reminder: $trimmed" }
            viewModelScope.launch {
                try {
                    repository.createReminder(trimmed)
                    _state.value = repository.reminders
                    logger.i { "Reminder created, total: ${_state.value.size}" }
                } catch (e: Exception) {
                    logger.e(e) { "Failed to create reminder" }
                }
            }
        }
    }

    fun markReminder(id: Long, isCompleted: Boolean) {
        logger.d { "Marking reminder id=$id, isCompleted=$isCompleted" }
        viewModelScope.launch {
            try {
                repository.markReminder(id, isCompleted)
                _state.value = repository.reminders
                logger.i { "Reminder $id marked" }
            } catch (e: Exception) {
                logger.e(e) { "Failed to mark reminder" }
            }
        }
    }
}