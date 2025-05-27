package de.syntax_institut.androidabschlussprojekt.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.data.local.model.enums.TabItem

@Composable
fun TabBar(
    activeTab: TabItem,
    onTabSelected: (TabItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .width(300.dp)
                .height(50.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(Color.Gray.copy(0.7f))      // opacity
        ) {
            TabItem.entries.forEach { tab ->
                TabBarButton(
                    title = stringResource(id = tab.titleResId),
                    isActive = tab == activeTab,
                    onClick = { onTabSelected(tab) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TabBarPreview() {
    // Use Theme here
    TabBar(
        activeTab = TabItem.HOME,
        onTabSelected = {}
    )
}