package com.example.kpmapp_with_di

import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.kpmapp_with_di.di.initKoin
import com.example.kpmapp_with_di.ui.root.AppScaffold
import com.example.kpmapp_with_di.ui.theme.AppTheme

@Composable
@Preview
fun App() {
    AppTheme {
        initKoin();

        AppScaffold()
    }
}