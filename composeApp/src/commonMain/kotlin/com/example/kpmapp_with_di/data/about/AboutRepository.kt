package com.example.kpmapp_with_di.data.about

import kotlin.math.max
import kotlin.math.min

internal class AboutRepository(
    private val platform: Platform
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
}