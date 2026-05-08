package com.example.kpmapp_with_di.ui.about

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kpmapp_with_di.data.about.AboutRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Stable
internal class AboutViewModel(
    private val repository: AboutRepository
) : ViewModel() {

    private val _state = MutableStateFlow<List<Pair<String, String>>>(emptyList())
    val state: StateFlow<List<Pair<String, String>>> = _state

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _state.value = repository.getAbout()
        }
    }
}