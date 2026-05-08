package com.example.kpmapp_with_di.di

import com.example.kpmapp_with_di.data.about.AboutRepository
import com.example.kpmapp_with_di.data.about.Platform
import com.example.kpmapp_with_di.ui.about.AboutViewModel
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModel

val appModule = module {

    single { AboutRepository() }
    single { Platform() }

    viewModel {
        AboutViewModel(get())
    }
}