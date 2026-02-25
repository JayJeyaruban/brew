package com.jayjeyaruban.brew.domain.brew

import com.jayjeyaruban.brew.domain.ExistingOrCreate
import com.jayjeyaruban.brew.domain.Mass
import com.jayjeyaruban.brew.domain.recipe.RecipeId
import com.jayjeyaruban.brew.domain.recipe.RecipeSchema
import kotlin.time.Duration
import kotlin.time.Instant

data class SaveBrew(
    val recipe: ExistingOrCreate<RecipeId, RecipeSchema.CreateRequest>,
    val output: Mass,
    val recordedTime: Instant,
    val extractionTime: Duration,
    val impression: Impression,
)
