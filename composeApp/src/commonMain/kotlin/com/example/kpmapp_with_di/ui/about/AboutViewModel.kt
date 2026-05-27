package com.example.kpmapp_with_di.ui.about

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.example.kpmapp_with_di.data.about.AboutRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.char

@Stable
internal class AboutViewModel(
    private val repository: AboutRepository
) : ViewModel() {
    private val logger = Logger.withTag("AboutViewModel")

    private val format: DateTimeFormat<LocalDateTime> = LocalDateTime.Format {
        day()
        char('.')
        monthNumber()
        char('.')
        year()
        char(' ')
        hour()
        char(':')
        minute()
    }

    private val _state = MutableStateFlow(AboutState())
    val state: StateFlow<AboutState> = _state.asStateFlow()

    init {
        logger.i { "AboutViewModel initialized" }

        repository.increaseVisitCount()
        logger.d { "Visited count increased" }

        repository.updateVisitedDate()
        logger.d { "Visited date updated" }

        loadData()
    }

    private fun loadData() {
        logger.d { "Loading About screen data..." }
        viewModelScope.launch {
            try {
                val platformInfo = repository.getAbout()
                val visitedCount = repository.visitedCount()
                val visitedDate = repository.visitedDate()?.format(format) ?: "—"

                logger.d {
                    "Loaded data: count=$visitedCount, date=$visitedDate"
                }

                _state.update { current ->
                    current.copy(
                        platformInfo = platformInfo,
                        visitedCount = visitedCount,
                        visitedDate = visitedDate
                    )
                }

                logger.i { "State updated successfully" }
            } catch (e: Exception) {
                logger.e(e) { "Failed to load About data" }
            }
        }
    }
}