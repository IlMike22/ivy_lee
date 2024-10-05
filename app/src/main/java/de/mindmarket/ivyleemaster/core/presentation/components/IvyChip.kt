package de.mindmarket.ivyleemaster.core.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import de.mindmarket.ivyleemaster.ui.theme.IvyLeeMasterTheme

@Composable
fun IvyChip(
    label: String,
    isSelected: Boolean,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selected by remember { mutableStateOf(isSelected) }
    FilterChip(
        modifier = modifier,
        onClick = {
            onClick()
            selected = !selected
        },
        selected = isSelected,
        label = {
            Text(text = label)
        },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                Modifier.size(AssistChipDefaults.IconSize)
            )
        }
    )
}

@Preview
@Composable
private fun IvyChipPreview() {
    IvyLeeMasterTheme {
        IvyChip("My great chip!", icon = Icons.Filled.Settings, onClick = {}, isSelected = true)
    }
}