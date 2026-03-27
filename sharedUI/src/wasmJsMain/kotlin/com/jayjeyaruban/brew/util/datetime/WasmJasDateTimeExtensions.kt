package com.jayjeyaruban.brew.util.datetime

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlin.time.Instant

@OptIn(ExperimentalWasmJsInterop::class)
private fun intlFormatMediumShort(millis: Double, tzId: String): String =
    js("""
      new Intl.DateTimeFormat(undefined, {
        dateStyle: "medium",
        timeStyle: "short",
        timeZone: tzId
      }).format(new Date(millis))
    """)

actual fun Instant.localizedString(tz: TimeZone): String {
    return intlFormatMediumShort(
        millis = toEpochMilliseconds().toDouble(),
        tzId = tz.id
    )
}
