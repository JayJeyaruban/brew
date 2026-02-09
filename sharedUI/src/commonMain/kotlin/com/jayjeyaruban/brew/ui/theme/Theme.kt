package com.jayjeyaruban.brew.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.materialkolor.DynamicMaterialTheme

typealias Theme = MaterialTheme

@Composable
internal fun Theme(
    isDark: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    DynamicMaterialTheme(
        seedColor = Color(0xFFFF9249),
        isDark = isDark,
        content = { Surface(content = content) }
        )
}

object Spacing {
    val XCompact = 4.dp
    val Compact = 8.dp
    val Standard = 16.dp
    val Spacious = 24.dp
    val XSpacious = 48.dp
    val XXSpacious = 72.dp
}
