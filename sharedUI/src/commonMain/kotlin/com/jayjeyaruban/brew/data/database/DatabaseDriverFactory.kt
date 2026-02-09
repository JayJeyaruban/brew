package com.jayjeyaruban.brew.data.database

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema

fun interface DatabaseDriverFactory {
    fun createDriver(schema:  SqlSchema<QueryResult.Value<Unit>>, databaseName: String): SqlDriver
}
