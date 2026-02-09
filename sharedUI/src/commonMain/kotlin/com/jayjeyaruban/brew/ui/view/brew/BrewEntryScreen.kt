package com.jayjeyaruban.brew.ui.view.brew

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.jayjeyaruban.brew.ui.theme.Spacing
import com.jayjeyaruban.brew.ui.theme.Theme
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

@Composable
fun BrewEntryScreen() {
    Scaffold(topBar = { TopAppBar({ Text("Brew") }) }) { padding ->
        Column(Modifier.padding(padding).padding(Spacing.Standard),
            verticalArrangement = Arrangement.spacedBy(Spacing.Spacious)) {
            Column(verticalArrangement = Arrangement.spacedBy(Spacing.Standard)) {
                val (bean, setBean) = rememberSaveable { mutableStateOf("") }
                val (grinder, setGrinder) = rememberSaveable { mutableStateOf("") }
                val (grindSetting, setGrindSetting) = rememberSaveable { mutableStateOf<Long?>(null) }
                val (dose, setDose) = rememberSaveable { mutableStateOf<Long?>(null) }
                val (targetOutput, setTargetOutput) = rememberSaveable { mutableStateOf<Long?>(null) }

                Text("Recipe", style = Theme.typography.titleLarge)

                TextField(bean, setBean, Modifier.fillMaxWidth(), label = {Text("Bean")})
                TextField(grinder, setGrinder, Modifier.fillMaxWidth(), label = {Text("Grinder")})
                TextField(grindSetting?.toString() ?: "", {setGrindSetting(it.toLong())}, Modifier.fillMaxWidth(), label = {Text("Grind setting")})

                Row(horizontalArrangement = Arrangement.spacedBy(Spacing.Compact)) {
                    TextField(dose?.toString() ?: "", {setDose(it.toLong())}, Modifier.weight(1f), label = {Text("Dose")}, keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number))

                    TextField(targetOutput?.toString() ?: "", {setTargetOutput(it.toLong())},
                        Modifier.weight(1f), label = {Text("Target Yield")}, keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number))
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(Spacing.Standard)) {
                val (output, setOutput) =  rememberSaveable { mutableStateOf<Long?>(null) }
                val (recordedAt, setWhen) = rememberSerializable { mutableStateOf(Clock.System.now()) }
                val (extractionTime, setExtractionTime) = rememberSaveable { mutableStateOf<Long?>(null) }
                Text("Brew", style = Theme.typography.titleLarge)

                TextField(output?.toString() ?: "", {setOutput(it.toLong())}, Modifier.fillMaxWidth(), label = {Text("Yield (g)")}, keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number))

                TextField(recordedAt.toLocalDateTime(TimeZone.currentSystemDefault()).toString(), {},
                    Modifier.fillMaxWidth(), label = {Text("When")})

                TextField(extractionTime?.toString() ?: "", {setExtractionTime(it.toLong())}, Modifier.fillMaxWidth(), label = {Text("Extraction time (s)")}, keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number))
            }
        }
    }
}

@Preview
@Composable
private fun BrewEntryScreenPreview() {
    Theme {
        BrewEntryScreen()
    }
}
