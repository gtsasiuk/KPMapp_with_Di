package com.example.kpmapp_with_di

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "KPMapp_with_Di",
    ) {
        App()
    }
}