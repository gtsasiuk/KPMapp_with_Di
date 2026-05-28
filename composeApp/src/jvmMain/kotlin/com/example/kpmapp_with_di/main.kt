package com.example.kpmapp_with_di

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.kpmapp_with_di.di.initKoin

fun main() = application {
    initKoin()
    Window(
        onCloseRequest = ::exitApplication,
        title = "KPMapp_with_Di",
    ) {
        App()
    }
}