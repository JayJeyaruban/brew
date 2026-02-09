package com.jayjeyaruban.brew.ui.view.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import com.jayjeyaruban.brew.domain.RecentBrew
import com.jayjeyaruban.brew.ui.theme.Spacing
import com.jayjeyaruban.brew.ui.theme.Theme
import org.jetbrains.compose.resources.vectorResource

@Composable
fun HomeScreen(
    recentBrews: List<RecentBrew>,
    onFabPress: () -> Unit,
    onItemPress: (RecentBrew.RecipeId) -> Unit,
) {
    val mostRecent = recentBrews.firstOrNull()
    val remainingRecent = recentBrews.drop(1)

    Scaffold(
        topBar = { TopAppBar({ Text("Brew log") }) },
        floatingActionButton = {
            mostRecent?.let {
                ExtendedFloatingActionButton({ Text("Log a brew") }, {
                    Icon(
                        vectorResource(
                            Res.drawable.coffee_24px
                        ), null
                    )
                }, onFabPress)
            }
        }
    ) { scaffoldPadding ->
        LazyColumn(
            Modifier.padding(scaffoldPadding)
                .padding(horizontal = Spacing.Standard)
                .padding(bottom = Spacing.Standard)
        ) {
            item {
                if (mostRecent == null) {
                    Card(Modifier.clickable(true, onClick = onFabPress)) {
                        Column(Modifier.fillMaxWidth().padding(Spacing.Standard),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(Spacing.Compact)
                        ) {
                            Text("No brews logged", style = Theme.typography.titleLarge)
                            Text("Tap here to log your first!")
                        }
                    }
                } else {
                    Card(Modifier.fillMaxWidth().clickable(true) { onItemPress(mostRecent.recipeId) }) {
                        Column(Modifier.padding(Spacing.Standard)) {
                            Text("Last Brew", style = Theme.typography.titleMedium)
                            Text(mostRecent.beanName)
                            Row(Modifier.padding(top = Spacing.XCompact)) {
                                mostRecent.ImpressionIcon()
                                Spacer(Modifier.size(Spacing.XCompact))
                                Text("${mostRecent.beanName} -> ${mostRecent.output} in ${mostRecent.brewTime}", Modifier.align(Alignment.CenterVertically))
                            }
                        }
                    }
                }
            }

            if (remainingRecent.isNotEmpty()) {
            item {
                Spacer(Modifier.size(Spacing.XSpacious))
                Text(
                    "Previous Brews",
                    style = Theme.typography.titleMedium
                )
            }

                itemsIndexed(remainingRecent) { i, brew ->
                    ListItem(
                        { Text("${brew.beanName} ${brew.dose} -> ${brew.output} in ${brew.brewTime}") },
                        leadingContent = { brew.ImpressionIcon() },
                        modifier = Modifier.clickable(true) { onItemPress (brew.recipeId) }
                    )
                    if (i < remainingRecent.lastIndex) {
                        Box(Modifier.padding(horizontal = Spacing.Standard)) {
                            HorizontalDivider()
                        }
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
        HomeScreen(emptyList(), {}, {})
    }
}

@Composable
private fun RecentBrew.ImpressionIcon(modifier: Modifier = Modifier) {
    val vectorImage = vectorResource(when (impression) {
        RecentBrew.Impression.Positive -> Res.drawable.sentiment_satisfied_24px
        RecentBrew.Impression.Negative -> Res.drawable.sentiment_dissatisfied_24px
        RecentBrew.Impression.Neutral -> Res.drawable.sentiment_neutral_24px
    })
    Icon(vectorImage, null, modifier)
}
