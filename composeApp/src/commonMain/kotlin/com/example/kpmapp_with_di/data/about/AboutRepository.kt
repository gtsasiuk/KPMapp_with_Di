package com.example.kpmapp_with_di.data.about

import com.example.kpmapp_with_di.data.common.preferences.Preferences
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.math.max
import kotlin.math.min
import kotlin.time.Clock

internal class AboutRepository(
    private val platform: Platform,
    private val preferences: Preferences
) {
    fun getAbout(): List<Pair<String, String>> {
        val items = mutableListOf(
            "Operating System" to "${platform.osName} ${platform.osVersion}",
            "Device" to platform.deviceModel,
            "CPU" to platform.cpuType
        )

        val max = max(platform.screen.width, platform.screen.height)
        val min = min(platform.screen.width, platform.screen.height)

        var display = "${max}×${min}"
        platform.screen.density?.let {
            display += " ${it}x"
        }

        items.add("Display" to display)

        return items
    }

    fun increaseVisitCount() {
        preferences.aboutVisitedCount++
    }

    fun visitedCount(): Int {
        return preferences.aboutVisitedCount
    }

    fun visitedCountObservable(): Flow<Int> {
        return preferences.observableAboutVisitedCount
    }

    fun updateVisitedDate() {
        preferences.aboutVisitedDate = Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
    }

    fun visitedDate(): LocalDateTime? {
        return preferences.aboutVisitedDate
    }
}