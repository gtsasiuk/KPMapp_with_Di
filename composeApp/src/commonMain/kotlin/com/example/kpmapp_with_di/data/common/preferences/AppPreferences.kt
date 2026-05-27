package com.example.kpmapp_with_di.data.common.preferences

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.getIntFlow
import com.russhwolf.settings.get
import com.russhwolf.settings.serialization.decodeValue
import com.russhwolf.settings.serialization.encodeValue
import com.russhwolf.settings.set
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.ExperimentalSerializationApi
import kotlin.time.Clock

@OptIn(ExperimentalSettingsApi::class)
class AppPreferences(
    val settings: Settings,
    val observableSettings: ObservableSettings
) : Preferences {
    val aboutVisitedDateChannel = Channel<LocalDateTime>()

    override var aboutVisitedCount: Int
        get() = settings.get<Int>(PreferenceKey.ABOUT_VISITED_COUNT.key) ?: 0
        set(value) {
            settings.set(PreferenceKey.ABOUT_VISITED_COUNT.key, value)
        }

    @OptIn(ExperimentalSerializationApi::class)
    override var aboutVisitedDate: LocalDateTime?
        get() = try {
            settings.decodeValue(
                LocalDateTime.serializer(),
                PreferenceKey.ABOUT_VISITED_DATE.key,
                Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            )
        } catch (e: Exception) { null }
        set(value) {
            val dt = value ?: Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            settings.encodeValue(
                LocalDateTime.serializer(),
                PreferenceKey.ABOUT_VISITED_DATE.key,
                dt
            )
            value?.let { aboutVisitedDateChannel.trySend(it) }
        }

    override val observableAboutVisitedCount: Flow<Int>
        get() = observableSettings.getIntFlow(PreferenceKey.ABOUT_VISITED_COUNT.key, 0)

    override val observableAboutVisitedDate: Flow<LocalDateTime>
        get() = aboutVisitedDateChannel.receiveAsFlow()

    override fun cleanStorage() {
        settings.clear()
    }
}