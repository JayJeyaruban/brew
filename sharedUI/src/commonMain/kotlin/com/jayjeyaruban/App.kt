package com.jayjeyaruban

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import brew.sharedui.generated.resources.Res
import brew.sharedui.generated.resources.coffee_24px
import brew.sharedui.generated.resources.sentiment_dissatisfied_24px
import brew.sharedui.generated.resources.sentiment_neutral_24px
import brew.sharedui.generated.resources.sentiment_satisfied_24px
import com.jayjeyaruban.theme.Theme
import com.jayjeyaruban.theme.Padding
import org.jetbrains.compose.resources.vectorResource
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@Preview
@Composable
fun App() = Theme {
    Scaffold(
        topBar = {TopAppBar({Text("Brew log")})},
        floatingActionButton = { ExtendedFloatingActionButton({Text("Log a brew")}, { Icon(vectorResource(
            Res.drawable.coffee_24px), null) }, {}) }
        ) { scaffoldPadding ->
        LazyColumn(Modifier.padding(scaffoldPadding)
            .padding(horizontal = Padding.Standard)
            .padding(bottom = Padding.Standard)) {
            item {
                SampleData.firstOrNull()?.let { top ->
                    Card(Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(Padding.Standard)) {
                            Text("Last Brew")
                            Text(top.bean)
                            Text("UP ${top.beanIn} -> ${top.out} in ${top.time}")
                        }
                    }
                }
            }

            item {
                Spacer(Modifier.size(Padding.XSpacious))
                Text("Recent Brews", style = Theme.typography.titleMedium)
            }

            val remaining =SampleData.drop(1)
            itemsIndexed(remaining) { i, brew ->
                ListItem({Text("${brew.bean} ${brew.beanIn} -> ${brew.out} in ${brew.time}")}, leadingContent = {brew.ImpressionIcon()})
                if (i < remaining.lastIndex) {
                    Box(Modifier.padding(horizontal = Padding.Standard)) {
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}

private val SampleData = listOf(
    BrewLog(
        "Coffee A",
        18,
        36,
        28.seconds,
        BrewLog.Impression.Positive,
    ),
    BrewLog(
        "KTH",
        18,
        38,
        28.seconds,
        BrewLog.Impression.Positive,
    ),
    BrewLog(
        "WH",
        18,
        34,
        23.seconds,
        BrewLog.Impression.Neutral,
    )
)

data class BrewLog(
    val bean: String,
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

@Composable
private fun BrewLog.ImpressionIcon(modifier: Modifier = Modifier) {
    val vectorImage = vectorResource(when (impression) {
        BrewLog.Impression.Positive -> Res.drawable.sentiment_satisfied_24px
        BrewLog.Impression.Negative -> Res.drawable.sentiment_dissatisfied_24px
        BrewLog.Impression.Neutral -> Res.drawable.sentiment_neutral_24px
    })
    Icon(vectorImage, null, modifier)
}
