package com.jayjeyaruban.brew.domain

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
@JvmInline
value class Mass(val value: Long)
