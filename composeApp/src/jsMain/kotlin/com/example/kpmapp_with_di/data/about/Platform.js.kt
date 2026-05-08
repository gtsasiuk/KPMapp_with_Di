package com.example.kpmapp_with_di.data.about

import kotlinx.browser.window

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class Platform {

    actual val osName: String =
        "Web"

    actual val osVersion: String =
        window.navigator.userAgent

    actual val deviceModel: String =
        "Browser"

    actual val cpuType: String =
        "JavaScript Engine"

    actual val screen: ScreenInfo =
        ScreenInfo()

    actual fun logSystemInfo() {

        println(
            "$osName $osVersion $deviceModel $cpuType"
        )
    }
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class ScreenInfo {

    actual val width: Int
        get() = window.screen.width

    actual val height: Int
        get() = window.screen.height

    actual val density: Int?
        get() = null
}