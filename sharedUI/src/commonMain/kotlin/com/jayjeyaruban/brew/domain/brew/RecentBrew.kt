package com.jayjeyaruban.brew.domain.brew

import com.jayjeyaruban.brew.data.database.models.RecentBrews
import com.jayjeyaruban.brew.domain.Mass
import com.jayjeyaruban.brew.domain.recipe.RecipeId
import kotlin.time.Duration
import kotlin.time.Duration.Companion.microseconds

data class RecentBrew(
    val recipeId: RecipeId,
    val beanName: String,
    val dose: Mass,
    val output: Mass,
    val brewTime: Duration,
    val impression: Impression,
) {
    companion object {
        fun fromPersistence(persistence: RecentBrews) = RecentBrew(
            recipeId = RecipeId(persistence.recipeId),
            beanName = persistence.beanName,
            dose = Mass(persistence.dose),
            output = Mass(persistence.output),
            brewTime = persistence.brewTime.microseconds,
            impression = enumValueOf(persistence.impression),
        )
    }
}
