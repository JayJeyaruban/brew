package com.jayjeyaruban.brew.domain

import kotlin.time.Duration

data class BrewLog(
    val bean: Bean,
    val beanIn: Int,
    val out: Int,
    val time: Duration,
    val impression: Impression,
) {
    enum class Impression {
        Positive,
        Negative,
        Neutral,
    }
}