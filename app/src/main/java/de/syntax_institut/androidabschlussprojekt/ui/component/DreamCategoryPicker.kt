package de.syntax_institut.androidabschlussprojekt.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.data.local.model.enums.DreamCategory

@Composable
fun DreamCategoryPicker(
    selectedCategory: DreamCategory,
    onClickSelected: (DreamCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(R.string.type_of_dream).uppercase())
        LazyRow(
            modifier = Modifier
        ) {
            items(DreamCategory.entries.size) { index ->
                val category = DreamCategory.entries[index]

                Button(
                    onClick = {},
                    modifier = Modifier
                        .padding(4.dp)
                        .width(150.dp)
//                        .border(
//                            width =
//                        )
                ) {
                    Text(stringResource(id = category.titleResId))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DreamCategoryPickerPreview() {
    // Use Theme here
    DreamCategoryPicker(
        selectedCategory = DreamCategory.NORMAL,
        onClickSelected = {}
    )
}