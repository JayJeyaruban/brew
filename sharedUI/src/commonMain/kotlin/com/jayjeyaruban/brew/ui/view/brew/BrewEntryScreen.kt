package com.jayjeyaruban.brew.ui.view.brew

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import brew.sharedui.generated.resources.Res
import brew.sharedui.generated.resources.close_24px
import brew.sharedui.generated.resources.save_24px
import brew.sharedui.generated.resources.sentiment_dissatisfied_24px
import brew.sharedui.generated.resources.sentiment_neutral_24px
import brew.sharedui.generated.resources.sentiment_satisfied_24px
import com.jayjeyaruban.brew.domain.ExistingOrCreate
import com.jayjeyaruban.brew.domain.Mass
import com.jayjeyaruban.brew.domain.bean.BeanSchema
import com.jayjeyaruban.brew.domain.brew.Impression
import com.jayjeyaruban.brew.domain.brew.SaveBrew
import com.jayjeyaruban.brew.domain.recipe.EspressoMachineSchema
import com.jayjeyaruban.brew.domain.recipe.GrinderSchema
import com.jayjeyaruban.brew.domain.recipe.RecipeSchema
import com.jayjeyaruban.brew.ui.theme.Spacing
import com.jayjeyaruban.brew.ui.theme.Theme
import com.jayjeyaruban.brew.util.datetime.localizedString
import org.jetbrains.compose.resources.vectorResource
import kotlin.time.Clock
import kotlin.time.Duration.Companion.milliseconds


@Composable
fun BrewEntryScreen(
    selectedRecipe: RecipeSchema.Data?,
    setSelectedRecipe: (RecipeSchema.Data) -> Unit,
    recipeOptions: List<RecipeSchema.Data>,
    onCancel: () -> Unit,
    onSave: (brew: SaveBrew) -> Unit,
) {
    val (bean, setBean) = rememberSaveable { mutableStateOf("") }
    val (roaster, setRoaster) = rememberSaveable { mutableStateOf("") }
    val (grinder, setGrinder) = rememberSaveable { mutableStateOf("") }
    val (grindSetting, setGrindSetting) = rememberSaveable { mutableStateOf<Long?>(null) }
    val (dose, setDose) = rememberSaveable { mutableStateOf<Long?>(null) }

    val (espressoMachine, setEspressoMachine) = rememberSaveable { mutableStateOf("") }
    val (targetOutput, setTargetOutput) = rememberSaveable { mutableStateOf<Long?>(null) }

    val (output, setOutput) =  rememberSaveable { mutableStateOf<Long?>(null) }
    val (recordedAt, setWhen) = rememberSerializable { mutableStateOf(Clock.System.now()) }
    val (extractionTime, setExtractionTime) = rememberSaveable { mutableStateOf<Long?>(null) }
    val (impression, setImpression) = rememberSaveable { mutableStateOf<Impression?>(null) }

    val canSubmit = bean.isNotEmpty() && grinder.isNotEmpty() && dose != null && espressoMachine.isNotEmpty() && output != null &&extractionTime != null && impression != null

    Scaffold(
        topBar = { TopAppBar({ Text("Brew") }, navigationIcon = { IconButton(onCancel) {Icon(vectorResource(Res.drawable.close_24px), "Close")}}) },
        floatingActionButton = {
            if (canSubmit) {

            FloatingActionButton(onClick = {
            onSave(SaveBrew(
                recipe = ExistingOrCreate.Create(
                    RecipeSchema.CreateRequest(
                        ExistingOrCreate.Create(BeanSchema.CreateRequest(bean)),
                        ExistingOrCreate.Create(GrinderSchema.CreateRequest(grinder)),
                        ExistingOrCreate.Create(EspressoMachineSchema.CreateRequest(espressoMachine)),
                    Mass(dose),
                    targetOutput?.let { Mass(it) }
                )),
                    Mass(output),
                    recordedAt,
                    extractionTime.milliseconds,
                    impression
                )
            )
        }) {
                    Icon(vectorResource(Res.drawable.save_24px), "Save brew") } }
        }
    ) { padding ->
        Column(Modifier
            .padding(padding)
            .padding(Spacing.Standard)
            .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(Spacing.Spacious)) {
            Column(verticalArrangement = Arrangement.spacedBy(Spacing.Standard)) {
                Text("Recipe", style = Theme.typography.titleLarge)

                selectedRecipe?.let { selectedOption ->
                    val textFieldState = rememberTextFieldState(selectedOption.summary() ?: "")
                    val (dropdownExpanded, setDropdownExpanded) = rememberSaveable { mutableStateOf(false) }
                    ExposedDropdownMenuBox(dropdownExpanded, setDropdownExpanded) {
                        OutlinedTextField(textFieldState, Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),readOnly = true)
                        ExposedDropdownMenu(
                            expanded = dropdownExpanded,
                            onDismissRequest = { setDropdownExpanded(false) },
                        ) {
                            recipeOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option.summary(), style = MaterialTheme.typography.bodyLarge) },
                                    onClick = {
                                        textFieldState.setTextAndPlaceCursorAtEnd(option.summary())
                                    },
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                )
                            }
                        }
                    }
                }

                TextField(bean, setBean, Modifier.fillMaxWidth(), label = {Text("Bean")})
                TextField(roaster, setRoaster, Modifier.fillMaxWidth(), label = {Text("Roaster")})
                TextField(grinder, setGrinder, Modifier.fillMaxWidth(), label = {Text("Grinder")})
                TextField(grindSetting?.toString() ?: "", {setGrindSetting(it.toLong())}, Modifier.fillMaxWidth(), label = {Text("Grind setting")})

                TextField(espressoMachine, setEspressoMachine, Modifier.fillMaxWidth(), label = {Text("Espresso machine")})

                Row(horizontalArrangement = Arrangement.spacedBy(Spacing.Compact)) {
                    TextField(dose?.toString() ?: "", {setDose(it.toLong())}, Modifier.weight(1f), label = {Text("Dose (g)")}, keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number))

                    TextField(targetOutput?.toString() ?: "", {setTargetOutput(it.toLong())},
                        Modifier.weight(1f), label = {Text("Target Yield (g)")}, keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number))
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(Spacing.Standard)) {
                Text("Brew", style = Theme.typography.titleLarge)

                TextField(output?.toString() ?: "", {setOutput(it.toLong())}, Modifier.fillMaxWidth(), label = {Text("Yield (g)")}, keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number))

                TextField(recordedAt.localizedString(), {},
                    Modifier.fillMaxWidth(), label = {Text("When")})

                TextField(extractionTime?.toString() ?: "", {setExtractionTime(it.toLong())}, Modifier.fillMaxWidth(), label = {Text("Extraction time (s)")}, keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number))

                Row {
                    Text("Impression:", Modifier.align(Alignment.CenterVertically))
                    Spacer(Modifier.size(Spacing.Compact))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween)) {
                        ToggleButton(
                            impression == Impression.Positive,
                            {setImpression(Impression.Positive)},
                            Modifier.weight(1f).semantics { role = Role.RadioButton },
                            shapes = ButtonGroupDefaults.connectedLeadingButtonShapes()
                            )
                            {
                            Icon(vectorResource(Res.drawable.sentiment_satisfied_24px), null)
                        }

                        ToggleButton(
                            impression == Impression.Neutral,
                            {setImpression(Impression.Neutral)},
                            Modifier.weight(1f).semantics { role = Role.RadioButton },
                            shapes = ButtonGroupDefaults.connectedMiddleButtonShapes()
                        )
                            {Icon(vectorResource(Res.drawable.sentiment_neutral_24px), null)}

                        ToggleButton(
                            impression == Impression.Negative,
                            {setImpression(Impression.Negative)},
                            Modifier.weight(1f).semantics { role = Role.RadioButton },
                            shapes = ButtonGroupDefaults.connectedTrailingButtonShapes()
                        )
                            {Icon(vectorResource(Res.drawable.sentiment_dissatisfied_24px), null)}
                    }
                }
            }
        }
    }
}

private fun RecipeSchema.Data.summary() = "${bean.name} - $dose"

@Preview
@Composable
private fun BrewEntryScreenPreview() {
    Theme {
//        BrewEntryScreen(DropDownOptions.Empty, {}, {}, {})
    }
}
