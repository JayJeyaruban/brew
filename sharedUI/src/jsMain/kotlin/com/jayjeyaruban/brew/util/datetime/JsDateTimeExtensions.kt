package com.jayjeyaruban.brew.util.datetime

import kotlinx.datetime.TimeZone
import kotlin.js.Date
import kotlin.js.js
import kotlin.time.Instant

actual fun Instant.localizedString(tz: TimeZone): String {
    val date = Date(toEpochMilliseconds().toDouble())

    val options = js("({})")
    options.dateStyle = "medium"
    options.timeStyle = "short"
    options.timeZone = tz.id

    val formatter = js("new Intl.DateTimeFormat(undefined, options)")
    return formatter.format(date) as String
}

