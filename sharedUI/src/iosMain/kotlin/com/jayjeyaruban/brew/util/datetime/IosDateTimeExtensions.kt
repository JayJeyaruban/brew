package com.jayjeyaruban.brew.util.datetime

import kotlinx.datetime.TimeZone
import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSDateFormatterMediumStyle
import platform.Foundation.NSDateFormatterShortStyle
import platform.Foundation.NSLocale
import platform.Foundation.NSTimeZone
import platform.Foundation.currentLocale
import platform.Foundation.dateWithTimeIntervalSince1970
import platform.Foundation.localTimeZone
import platform.Foundation.timeZoneWithName
import kotlin.time.Instant

actual fun Instant.localizedString(tz: TimeZone): String {
    val formatter = NSDateFormatter().apply {
        dateStyle = NSDateFormatterMediumStyle
        timeStyle = NSDateFormatterShortStyle
        locale = NSLocale.currentLocale
        timeZone = NSTimeZone.timeZoneWithName(tz.id) ?: NSTimeZone.localTimeZone
    }

    val date = NSDate.dateWithTimeIntervalSince1970(epochSeconds.toDouble())
    return formatter.stringFromDate(date)
}
