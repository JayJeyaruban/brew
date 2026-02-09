package com.jayjeyaruban.brew.di

import com.jayjeyaruban.brew.data.database.DatabaseDriverFactory
import com.jayjeyaruban.brew.db.BrewDatabase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


class AppGraph(
    df: DatabaseDriverFactory,
    ioDispatcher: CoroutineDispatcher,
    defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
) {
    val store = StoreGraph(ioDispatcher, df.createDriver(BrewDatabase.Schema, "brew.db"))
}

