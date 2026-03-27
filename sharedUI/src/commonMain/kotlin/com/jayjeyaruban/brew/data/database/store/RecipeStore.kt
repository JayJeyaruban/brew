package com.jayjeyaruban.brew.data.database.store

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.jayjeyaruban.brew.data.database.BrewDatabase
import com.jayjeyaruban.brew.domain.recipe.RecipeSchema
import com.jayjeyaruban.brew.domain.recipe.fromPersistence
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RecipeStore(val recentRecipes: Flow<List<RecipeSchema.Data>>)

fun recipeStore(db: BrewDatabase, dispatcher: CoroutineDispatcher) = RecipeStore(
    db.recipeQueries.recentRecipes().asFlow().mapToList(dispatcher).map { recipes -> recipes.map(RecipeSchema.Data::fromPersistence) }
)
