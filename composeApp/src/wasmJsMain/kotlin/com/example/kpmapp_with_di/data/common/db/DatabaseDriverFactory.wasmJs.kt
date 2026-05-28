package com.example.kpmapp_with_di.data.common.db

import app.cash.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory {
    actual fun create(): SqlDriver {
        throw UnsupportedOperationException("Web platform is not supported")
    }
}