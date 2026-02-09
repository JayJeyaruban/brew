package com.jayjeyaruban.brew

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.jayjeyaruban.brew.di.AppGraph
import com.jayjeyaruban.brew.domain.RecentBrew
import com.jayjeyaruban.brew.ui.theme.Theme
import com.jayjeyaruban.brew.ui.view.brew.BrewEntryScreen
import com.jayjeyaruban.brew.ui.view.home.HomeScreen
import kotlinx.coroutines.flow.map
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

@Composable
fun App(appGraph: AppGraph) = Theme {
    val appGraph = retain { appGraph }
    val recentBrews by appGraph.store.recentBrews.collectAsStateWithLifecycle(emptyList())
    val stack = rememberNavBackStack(SavedStateConfiguration { serializersModule = Navigation.Serializers }, Navigation.Home)

    NavDisplay(
        backStack =  stack,
        entryProvider = entryProvider {
        entry<Navigation.Home> {
            HomeScreen(recentBrews, {stack.add(Navigation.BrewEntry)}, {})
        }

            entry<Navigation.BrewEntry> {
                BrewEntryScreen()
            }
    })
}

object Navigation {
    @Serializable
    sealed interface Route: NavKey

    @Serializable
    data object Home: NavKey

    @Serializable
    data object BrewEntry: NavKey

    @OptIn(ExperimentalSerializationApi::class)
    val Serializers = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(Home::class)
            subclass(BrewEntry::class)
        }
    }
}
