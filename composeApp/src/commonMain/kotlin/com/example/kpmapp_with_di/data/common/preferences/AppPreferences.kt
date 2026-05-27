package com.example.kpmapp_with_di.data.common.preferences

import co.touchlab.kermit.Logger
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
    private val logger = Logger.withTag("AppPreferences")
    val aboutVisitedDateChannel = Channel<LocalDateTime>()

    override var aboutVisitedCount: Int
        get() {
            val value = settings.get<Int>(PreferenceKey.ABOUT_VISITED_COUNT.key) ?: 0
            logger.d { "Read aboutVisitedCount = $value" }
            return value
        }
        set(value) {
            logger.d { "Save aboutVisitedCount = $value" }
            settings.set(PreferenceKey.ABOUT_VISITED_COUNT.key, value)
        }

    @OptIn(ExperimentalSerializationApi::class)
    override var aboutVisitedDate: LocalDateTime?
        get() = try {
            val value = settings.decodeValue(
                LocalDateTime.serializer(),
                PreferenceKey.ABOUT_VISITED_DATE.key,
                Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            )
            logger.d { "Read aboutVisitedDate = $value" }

            value
        } catch (e: Exception) {
            logger.e(e) { "Failed to read aboutVisitedDate" }
            null
        }
        set(value) {
            val dt = value ?: Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            logger.d { "Save aboutVisitedDate = $dt" }

            settings.encodeValue(
                LocalDateTime.serializer(),
                PreferenceKey.ABOUT_VISITED_DATE.key,
                dt
            )
            value?.let {
                aboutVisitedDateChannel.trySend(it)
                logger.d { "Date emitted to flow" }
            }
        }

    override val observableAboutVisitedCount: Flow<Int>
        get() = observableSettings.getIntFlow(PreferenceKey.ABOUT_VISITED_COUNT.key, 0)

    override val observableAboutVisitedDate: Flow<LocalDateTime>
        get() = aboutVisitedDateChannel.receiveAsFlow()

    override fun cleanStorage() {
        settings.clear()
    }
}