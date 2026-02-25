package com.jayjeyaruban.brew.ui.components.button

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip

@Composable
fun <T> SegmentedIconButtonGroup(
    options: List<T>,
    selected: T?,
    onSelected: (T) -> Unit,
    modifier: Modifier = Modifier,
    icon: @Composable (T, Boolean) -> Unit,
) {
    require(options.size == 3) { "This implementation expects exactly 3 options." }

    val radius = 12.dp

    Row(modifier) {
        options.forEachIndexed { index, option ->
            val isSelected = option == selected

            val shape: Shape = when (index) {
                0 -> RoundedCornerShape(topStart = radius, bottomStart = radius)
                options.lastIndex -> RoundedCornerShape(topEnd = radius, bottomEnd = radius)
                else -> RoundedCornerShape(0.dp)
            }

            val buttonModifier = Modifier
                .clip(shape)

            OutlinedIconButton(
                onClick = { onSelected(option) },
                modifier = buttonModifier,
                shape = shape,
                // Make the middle borders overlap so it looks like one connected control.
                border = androidx.compose.material3.ButtonDefaults.outlinedButtonBorder().copy(
                    width = 1.dp,
//                    color = MaterialTheme.colorScheme.outline
                ),
                colors = androidx.compose.material3.IconButtonDefaults.outlinedIconButtonColors(
                    containerColor = if (isSelected) MaterialTheme.colorScheme.secondaryContainer
                    else MaterialTheme.colorScheme.surface,
                    contentColor = if (isSelected) MaterialTheme.colorScheme.onSecondaryContainer
                    else MaterialTheme.colorScheme.onSurface
                )
            ) {
                icon(option, isSelected)
            }

            // Negative padding to collapse adjacent borders so it looks connected
            if (index != options.lastIndex) {
                androidx.compose.foundation.layout.Spacer(Modifier.padding(end = (-1).dp))
            }
        }
    }
}
