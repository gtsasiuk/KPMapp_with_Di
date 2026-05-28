package com.example.kpmapp_with_di.data.common.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.example.kpmapp_with_di.AppDatabase

actual class DatabaseDriverFactory {
    actual fun create(): SqlDriver =
        JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).also {
            AppDatabase.Schema.create(it)
        }
}