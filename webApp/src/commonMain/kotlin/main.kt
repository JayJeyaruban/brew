import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.jayjeyaruban.brew.App
import com.jayjeyaruban.brew.data.database.DatabaseDriverFactory
import com.jayjeyaruban.brew.di.AppGraph
import kotlinx.coroutines.Dispatchers

@OptIn(ExperimentalComposeUiApi::class)
fun main() = ComposeViewport { App(AppGraph(DatabaseDriverFactory { schema, db ->
    TODO()
}, Dispatchers.Default)) }
