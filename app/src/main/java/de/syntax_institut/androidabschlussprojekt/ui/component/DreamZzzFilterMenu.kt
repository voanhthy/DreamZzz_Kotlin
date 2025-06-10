package de.syntax_institut.androidabschlussprojekt.ui.component

import android.R.attr.bottom
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.syntax_institut.androidabschlussprojekt.data.local.model.enums.DreamCategory
import de.syntax_institut.androidabschlussprojekt.data.local.model.enums.ImageStyle
import de.syntax_institut.androidabschlussprojekt.data.local.model.enums.Mood
import de.syntax_institut.androidabschlussprojekt.ui.viewmodel.DreamViewModel
import org.koin.androidx.compose.koinViewModel
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.ui.theme.DreamZzzGray
import de.syntax_institut.androidabschlussprojekt.ui.theme.DreamZzzLavender


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DreamZzzFilterMenu(
    dreamViewModel: DreamViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    // Filter ausklappen
    var showMoodFilter by remember { mutableStateOf(false) }
    var showDreamCategoryFilter by remember { mutableStateOf(false) }
    var showImageStyleFilter by remember { mutableStateOf(false) }
    var showSortFilter by remember { mutableStateOf(false) }

    val sortAsc by dreamViewModel.sortAsc.collectAsState()
    val selectedMoodsFilter by dreamViewModel.selectedMoodsFilter.collectAsState()
    val selectedCategoriesFilter by dreamViewModel.selectedCategoriesFilter.collectAsState()
    val selectedImageStylesFilter by dreamViewModel.selectedImageStylesFilter.collectAsState()
    val gridColumns by dreamViewModel.gridColums.collectAsState()

    val sortOptions = listOf("Neueste zuerst", "Älteste zuerst")

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(DreamZzzGray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text("Filter",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold)

            Spacer(modifier = Modifier.padding(8.dp))

            // Slider um Grid Spalten anzupassen
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("Galerie Spaltenanzahl",
                        fontWeight = FontWeight.SemiBold)

                    Slider(
                        value = gridColumns.toFloat(),
                        onValueChange = { newValue ->
                            dreamViewModel.setGridColumns(newValue.toInt())
                        },
                        valueRange = 1f..8f,
                        steps = 7,
                        colors = SliderDefaults.colors(
                            thumbColor = DreamZzzLavender,
                            activeTrackColor = DreamZzzLavender,
                            inactiveTrackColor = DreamZzzLavender,
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.padding(8.dp))

            // nach Datum auf- oder absteigend sortieren
            ExpandableFilterSection(
                title = "nach Datum sortieren",
                isExpanded = showSortFilter,
                onToggleExpand = { showSortFilter = it }
            ) {
                Column {
                    // Neueste zuerst
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { dreamViewModel.setSortArc(true) }
                            .padding(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = sortAsc,
                            onClick = { dreamViewModel.setSortArc(true) },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = DreamZzzLavender,
                                unselectedColor = DreamZzzLavender
                            )
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                        Text(sortOptions[0])
                    }

                    // Älteste zuerst
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { dreamViewModel.setSortArc(true) }
                            .padding(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = !sortAsc,
                            onClick = { dreamViewModel.setSortArc(false) },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = DreamZzzLavender,
                                unselectedColor = DreamZzzLavender
                            )
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                        Text(sortOptions[1])
                    }
                }
            }

            Spacer(modifier = Modifier.padding(8.dp))

            // Mood Filter
            ExpandableFilterSection(
                title = "Stimmung",
                isExpanded = showMoodFilter,
                onToggleExpand = { showMoodFilter = it }
            ) {
                Column(
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { dreamViewModel.clearMoodsFilter() }
                            .padding(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = selectedMoodsFilter.isEmpty(),    // wenn leer, dann werden keine Filter angewandt -> "alle" ist ausgewählt
                            onCheckedChange = { isChecked ->
                                if (isChecked) {
                                    dreamViewModel.clearMoodsFilter()
                                }
                            },
                            modifier = Modifier.size(24.dp),
                            colors = CheckboxDefaults.colors(
                                checkedColor = DreamZzzLavender,
                                uncheckedColor = DreamZzzLavender,
                                checkmarkColor = Color.White
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Alle")
                    }

                    Mood.entries.forEach { mood ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { dreamViewModel.toggleMoodFilter(mood) }
                                .padding(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = selectedMoodsFilter.contains(mood),
                                onCheckedChange = { _ -> dreamViewModel.toggleMoodFilter(mood) },
                                modifier = Modifier.size(24.dp),
                                colors = CheckboxDefaults.colors(
                                    checkedColor = DreamZzzLavender,
                                    uncheckedColor = DreamZzzLavender,
                                    checkmarkColor = Color.White
                                )
                            )

                            Spacer(modifier = Modifier.width(8.dp))
                            Text(stringResource(mood.titleResId))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.padding(8.dp))

            // DreamCategory Filter
            ExpandableFilterSection(
                title = "Traumart",
                isExpanded = showDreamCategoryFilter,
                onToggleExpand = { showDreamCategoryFilter = it }
            ) {
                Column(
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { dreamViewModel.clearCategoriesFilter() }
                            .padding(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = selectedCategoriesFilter.isEmpty(),    // wenn leer, dann werden keine Filter angewandt -> "alle" ist ausgewählt
                            onCheckedChange = { isChecked ->
                                if (isChecked) {
                                    dreamViewModel.clearCategoriesFilter()
                                }
                            },
                            modifier = Modifier.size(24.dp),
                            colors = CheckboxDefaults.colors(
                                checkedColor = DreamZzzLavender,
                                uncheckedColor = DreamZzzLavender,
                                checkmarkColor = Color.White
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Alle")
                    }

                    DreamCategory.entries.forEach { category ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { dreamViewModel.toggleCategoryFilter(category) }
                                .padding(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = selectedCategoriesFilter.contains(category),
                                onCheckedChange = { _ -> dreamViewModel.toggleCategoryFilter(category) },
                                modifier = Modifier.size(24.dp),
                                colors = CheckboxDefaults.colors(
                                    checkedColor = DreamZzzLavender,
                                    uncheckedColor = DreamZzzLavender,
                                    checkmarkColor = Color.White
                                )
                            )

                            Spacer(modifier = Modifier.width(8.dp))
                            Text(stringResource(category.titleResId))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.padding(8.dp))

            // ImageStyle Filter
            ExpandableFilterSection(
                title = "Bildstil",
                isExpanded = showImageStyleFilter,
                onToggleExpand = { showImageStyleFilter = it }
            ) {
                Column(
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { dreamViewModel.clearImageStylesFilter() }
                            .padding(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = selectedImageStylesFilter.isEmpty(),    // wenn leer, dann werden keine Filter angewandt -> "alle" ist ausgewählt
                            onCheckedChange = { isChecked ->
                                if (isChecked) {
                                    dreamViewModel.clearImageStylesFilter()
                                }
                            },
                            modifier = Modifier.size(24.dp),
                            colors = CheckboxDefaults.colors(
                                checkedColor = DreamZzzLavender,
                                uncheckedColor = DreamZzzLavender,
                                checkmarkColor = Color.White
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Alle")
                    }

                    ImageStyle.entries.forEach { style ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { dreamViewModel.toggleImageStylesFilter(style) }
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = selectedImageStylesFilter.contains(style),
                                onCheckedChange = { _ -> dreamViewModel.toggleImageStylesFilter(style) },
                                modifier = Modifier.size(24.dp),
                                colors = CheckboxDefaults.colors(
                                    checkedColor = DreamZzzLavender,
                                    uncheckedColor = DreamZzzLavender,
                                    checkmarkColor = Color.White
                                )
                            )

                            Spacer(modifier = Modifier.width(4.dp))

                            Text(stringResource(style.titleResId))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.padding(16.dp))

            DreamZzzTextButton(
                onClickText = {},
                title = "Anwenden",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DreamZzzFilterMenuPreview() {
    // Use Theme here
    DreamZzzFilterMenu()
}