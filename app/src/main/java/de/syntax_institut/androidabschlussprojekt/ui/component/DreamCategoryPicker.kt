package de.syntax_institut.androidabschlussprojekt.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.data.local.model.enums.DreamCategory
import de.syntax_institut.androidabschlussprojekt.ui.theme.DreamZzzLavender
import de.syntax_institut.androidabschlussprojekt.ui.theme.SelectedCategoryBorder

@Composable
fun DreamCategoryPicker(
    selectedCategory: DreamCategory,
    onClickSelected: (DreamCategory) -> Unit,
    modifier: Modifier = Modifier
) {

    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(DreamCategory.entries.size) { index ->
            val category = DreamCategory.entries[index]
            val borderColor =
                if (category == selectedCategory) SelectedCategoryBorder else Color.Transparent
            val borderWidth = if (category == selectedCategory) 4.dp else 0.dp
            val buttonContainerColor = DreamZzzLavender
            val customShape = RoundedCornerShape(10.dp)

            Button(
                onClick = {
                    onClickSelected(category)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonContainerColor,
                    contentColor = Color.Black
                ),
                shape = customShape,
                modifier = Modifier
                    .width(160.dp)
                    .border(
                        width = borderWidth,
                        color = borderColor,
                        shape = customShape
                    )
            ) {
                Text(stringResource(id = category.titleResId))
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