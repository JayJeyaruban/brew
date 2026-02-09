package com.jayjeyaruban.brew.domain

import com.jayjeyaruban.brew.RecentBrews
import kotlin.jvm.JvmInline
import kotlin.time.Duration
import kotlin.time.Duration.Companion.microseconds

data class RecentBrew(
    val recipeId: RecipeId,
    val beanName: String,
    val dose: Dose,
    val output: Output,
    val brewTime: Duration,
    val impression: Impression
) {
    companion object {
        fun fromPersistence(persistence: RecentBrews) = RecentBrew(
            recipeId = RecipeId(persistence.recipeId),
            beanName = persistence.beanName,
            dose = Dose(persistence.dose),
            output = Output(persistence.output),
            brewTime = persistence.brewTime.microseconds,
            impression = enumValueOf(persistence.impression)
        )
    }

    @JvmInline
    value class RecipeId(val id: Long)


    @JvmInline
    value class Dose(val value: Long)

    @JvmInline
    value class Output(val value: Long)

    enum class Impression {
        Positive,
        Negative,
        Neutral,
    }
}
