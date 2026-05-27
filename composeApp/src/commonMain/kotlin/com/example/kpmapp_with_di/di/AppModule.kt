package com.example.kpmapp_with_di.di

import com.example.kpmapp_with_di.data.about.AboutRepository
import com.example.kpmapp_with_di.data.about.Platform
import com.example.kpmapp_with_di.data.common.preferences.AppPreferences
import com.example.kpmapp_with_di.data.common.preferences.Preferences
import com.example.kpmapp_with_di.ui.about.AboutViewModel
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.binds
import org.koin.dsl.module
import org.koin.plugin.module.dsl.single
import org.koin.plugin.module.dsl.viewModel

private fun createSettings(): Settings = Settings()

val dataModule = module {
    single<Settings> { createSettings() } binds arrayOf(Settings::class, ObservableSettings::class)
    singleOf(::AppPreferences) bind Preferences::class
}

val appModule = module {
    single<Platform>()
    single<AboutRepository>()
    viewModel<AboutViewModel>()
}