package com.jayjeyaruban.brew.di

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.db.SqlDriver
import com.jayjeyaruban.brew.db.BrewDatabase
import com.jayjeyaruban.brew.db.BrewDatabase.Companion.invoke
import com.jayjeyaruban.brew.domain.RecentBrew
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.map

class StoreGraph(
    dispatcher: CoroutineDispatcher,
    driver: SqlDriver
) {
    val db = BrewDatabase(driver)

    val recentBrews = db.brewQueries.recentBrews().asFlow().mapToList(dispatcher).map { brews -> brews.map {  RecentBrew.fromPersistence(it) } }
}