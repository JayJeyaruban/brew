package com.jayjeyaruban.brew

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.retain.retain
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.jayjeyaruban.brew.di.AppGraph
import com.jayjeyaruban.brew.domain.RecentBrew
import com.jayjeyaruban.brew.ui.theme.Theme
import com.jayjeyaruban.brew.ui.view.home.HomeScreen
import kotlinx.coroutines.flow.map

@Composable
fun App(appGraph: AppGraph) = Theme {
    val appGraph = retain { appGraph }
    val recentBrews by appGraph.store.recentBrews.collectAsStateWithLifecycle(emptyList())
    HomeScreen(recentBrews, {}, {})
}
