package com.jayjeyaruban.brew.di

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.db.SqlDriver
import com.jayjeyaruban.brew.data.database.BrewDatabase
import com.jayjeyaruban.brew.data.database.store.brewStore
import com.jayjeyaruban.brew.data.database.store.recipeStore
import com.jayjeyaruban.brew.domain.brew.RecentBrew
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.map

class StoreGraph(dispatcher: CoroutineDispatcher, driver: SqlDriver) {
    val db = BrewDatabase(driver)

    val brew = brewStore(db, dispatcher)

    val recipe = recipeStore(db, dispatcher)
}
