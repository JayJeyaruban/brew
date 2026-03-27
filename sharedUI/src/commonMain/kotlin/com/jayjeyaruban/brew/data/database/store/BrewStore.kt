package com.jayjeyaruban.brew.data.database.store

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.jayjeyaruban.brew.data.database.BrewDatabase
import com.jayjeyaruban.brew.domain.ExistingOrCreate
import com.jayjeyaruban.brew.domain.brew.RecentBrew
import com.jayjeyaruban.brew.domain.brew.SaveBrew
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class BrewStore(val recentBrews: Flow<List<RecentBrew>>, val saveBrew: suspend (request: SaveBrew) -> Unit)

fun brewStore(db: BrewDatabase, dispatcher: CoroutineDispatcher) = BrewStore(
    recentBrews = db.brewQueries.recentBrews().asFlow().mapToList(dispatcher).map { brews -> brews.map {  RecentBrew.fromPersistence(it) } },
    saveBrew = {request ->
        withContext(dispatcher) {
            db.transaction {
                val recipeId = when (val recipe = request.recipe) {
                    is ExistingOrCreate.Existing -> recipe.id.id
                    is ExistingOrCreate.Create -> {
                        val beanId = when (val bean = recipe.create.existingOrCreateBean) {
                            is ExistingOrCreate.Existing -> bean.id.id
                            is ExistingOrCreate.Create ->
                                db.beanQueries.insertBean(bean.create.name).executeAsOne()
                        }


                        val grinderId = recipe.create.existingOrCreateGrinder?.let {
                            when (it) {
                                is ExistingOrCreate.Existing -> it.id.id
                                is ExistingOrCreate.Create -> db.grinderQueries.insertGrinder(it.create.name).executeAsOne()

                        }
                        }

                        val espressoMachineId = when (val em = recipe.create.existingOrCreateEspressoMachine) {
                            is ExistingOrCreate.Existing -> em.id.id
                            is ExistingOrCreate.Create -> db.espressoMachineQueries.insertEspressoMachine(em.create.name).executeAsOne()
                        }

                        db.recipeQueries.insertRecipe(beanId, recipe.create.dose.value, grinderId, espressoMachineId, recipe.create.targetOutput?.value).executeAsOne()
                    }
                }

                db.brewQueries.insertBrew(recipeId, request.output.value, request.recordedTime.toEpochMilliseconds(), request.extractionTime.inWholeSeconds, request.impression.name)
            }
        }
    }
)
