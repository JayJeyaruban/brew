package com.jayjeyaruban.brew.ui.view.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import brew.sharedui.generated.resources.Res
import brew.sharedui.generated.resources.coffee_24px
import brew.sharedui.generated.resources.sentiment_dissatisfied_24px
import brew.sharedui.generated.resources.sentiment_neutral_24px
import brew.sharedui.generated.resources.sentiment_satisfied_24px
import com.jayjeyaruban.brew.domain.Bean
import com.jayjeyaruban.brew.domain.BrewLog
import com.jayjeyaruban.brew.ui.theme.Padding
import com.jayjeyaruban.brew.ui.theme.Theme
import org.jetbrains.compose.resources.vectorResource
import kotlin.time.Duration.Companion.seconds

@Composable
fun HomeScreen() {
    Scaffold(
        topBar = { TopAppBar({ Text("Brew log") }) },
        floatingActionButton = {
            ExtendedFloatingActionButton({ Text("Log a brew") }, {
                Icon(
                    vectorResource(
                        Res.drawable.coffee_24px
                    ), null
                )
            }, {})
        }
    ) { scaffoldPadding ->
        LazyColumn(
            Modifier.padding(scaffoldPadding)
                .padding(horizontal = Padding.Standard)
                .padding(bottom = Padding.Standard)
        ) {
            item {
                SampleData.firstOrNull()?.let { top ->
                    Card(Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(Padding.Standard)) {
                            Text("Last Brew", style = Theme.typography.titleMedium)
                            Text(top.bean.name)
                            Row(Modifier.padding(top = Padding.XCompact)) {
                                top.ImpressionIcon()
                                Spacer(Modifier.size(Padding.XCompact))
                                Text("${top.beanIn} -> ${top.out} in ${top.time}", Modifier.align(Alignment.CenterVertically))
                            }
                        }
                    }
                }
            }

            item {
                Spacer(Modifier.size(Padding.XSpacious))
                Text(
                    "Previous Brews",
                    style = Theme.typography.titleMedium
                )
            }

            val remaining = SampleData.drop(1)
            itemsIndexed(remaining) { i, brew ->
                ListItem(
                    { Text("${brew.bean.name} ${brew.beanIn} -> ${brew.out} in ${brew.time}") },
                    leadingContent = { brew.ImpressionIcon() })
                if (i < remaining.lastIndex) {
                    Box(Modifier.padding(horizontal = Padding.Standard)) {
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    Theme {
        HomeScreen()
    }
}

private val SampleData = listOf(
    BrewLog(
        Bean("Coffee A"),
        18,
        36,
        28.seconds,
        BrewLog.Impression.Positive,
    ),
    BrewLog(
        Bean("KTH"),
        18,
        38,
        28.seconds,
        BrewLog.Impression.Positive,
    ),
    BrewLog(
        Bean("WH"),
        18,
        34,
        23.seconds,
        BrewLog.Impression.Neutral,
    )
)

@Composable
private fun BrewLog.ImpressionIcon(modifier: Modifier = Modifier) {
    val vectorImage = vectorResource(when (impression) {
        BrewLog.Impression.Positive -> Res.drawable.sentiment_satisfied_24px
        BrewLog.Impression.Negative -> Res.drawable.sentiment_dissatisfied_24px
        BrewLog.Impression.Neutral -> Res.drawable.sentiment_neutral_24px
    })
    Icon(vectorImage, null, modifier)
}
