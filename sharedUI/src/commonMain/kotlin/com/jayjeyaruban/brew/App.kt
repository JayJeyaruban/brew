package com.jayjeyaruban.brew

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.retain.retain
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.jayjeyaruban.brew.di.AppGraph
import com.jayjeyaruban.brew.domain.recipe.RecipeId
import com.jayjeyaruban.brew.ui.theme.Theme
import com.jayjeyaruban.brew.ui.view.brew.BrewEntryScreen
import com.jayjeyaruban.brew.ui.view.home.HomeScreen
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import kotlin.jvm.JvmInline

@Composable
fun App(appGraph: AppGraph) = Theme {
    val appGraph = retain { appGraph }
    val recentBrews by appGraph.store.brew.recentBrews.collectAsStateWithLifecycle(emptyList())
    val stack = rememberNavBackStack(SavedStateConfiguration { serializersModule = Navigation.Serializers }, Navigation.Home)

    NavDisplay(
        backStack =  stack,
        entryProvider = entryProvider {
        entry<Navigation.Home> {
            HomeScreen(recentBrews, {stack.add(Navigation.BrewEntry())}, {stack.add(Navigation.BrewEntry(it))})
        }

            entry<Navigation.BrewEntry> { data ->
                val scope = rememberCoroutineScope()

                val recipes by appGraph.store.recipe.recentRecipes.collectAsStateWithLifecycle(emptyList())

                val (selectedRecipe, setSelectedRecipe) = retain { mutableStateOf(recipes.find { it.id == data.selected } ?: recipes.firstOrNull()) }

                BrewEntryScreen(
                    selectedRecipe,
                    setSelectedRecipe,
                    recipes,
                    {stack.removeLastOrNull()}, { req ->
                    scope.launch {
                        appGraph.store.brew.saveBrew(req)
                        stack.removeLastOrNull()
                    }
                })
            }
    })
}

object Navigation {
    @Serializable
    sealed interface Route : NavKey

    @Serializable
    data object Home : NavKey

    @Serializable
    @JvmInline
    value class BrewEntry(val selected: RecipeId? = null): NavKey

    @OptIn(ExperimentalSerializationApi::class)
    val Serializers =
        SerializersModule {
            polymorphic(NavKey::class) {
                subclass(Home::class)
                subclass(BrewEntry::class)
            }
        }
}
