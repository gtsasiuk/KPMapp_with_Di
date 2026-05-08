package com.example.kpmapp_with_di.data.about

import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.content.res.Resources

actual class Platform {

    actual val osName: String =
        "Android"

    actual val osVersion: String =
        Build.VERSION.RELEASE

    actual val deviceModel: String =
        "${Build.MANUFACTURER} ${Build.MODEL}"

    actual val cpuType: String =
        Build.SUPPORTED_ABIS.firstOrNull() ?: "Unknown CPU"

    actual val screen: ScreenInfo =
        ScreenInfo()

    actual fun logSystemInfo() {

        Log.d(
            "PlatformInfo",
            "$osName $osVersion $deviceModel $cpuType"
        )
    }
}


actual class ScreenInfo actual constructor() {

    private val metrics: DisplayMetrics =
        Resources.getSystem().displayMetrics

    actual val width: Int
        get() = metrics.widthPixels

    actual val height: Int
        get() = metrics.heightPixels

    actual val density: Int?
        get() = metrics.densityDpi
}