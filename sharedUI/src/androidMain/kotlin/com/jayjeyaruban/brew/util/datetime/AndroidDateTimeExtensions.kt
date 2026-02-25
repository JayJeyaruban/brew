package com.jayjeyaruban.brew.util.datetime

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toJavaZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import kotlin.time.Instant
import kotlin.time.toJavaInstant

actual fun Instant.localizedString(tz: TimeZone): String {
    val javaInstant = toJavaInstant()
    return DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
        .withZone(tz.toJavaZoneId())
            .withLocale(Locale.getDefault()).format(javaInstant)
}
