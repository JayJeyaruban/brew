package com.jayjeyaruban.brew.util.datetime

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

expect fun Instant.localizedString(tz: TimeZone = TimeZone.currentSystemDefault()): String

