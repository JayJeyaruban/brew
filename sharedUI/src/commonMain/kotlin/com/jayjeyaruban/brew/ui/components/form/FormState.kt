package com.jayjeyaruban.brew.ui.components.form

object Form {
    data class State<T>(
        val value: T,
        val error: String,
    )
}