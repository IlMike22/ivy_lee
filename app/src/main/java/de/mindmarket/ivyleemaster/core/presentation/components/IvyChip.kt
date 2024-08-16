package de.mindmarket.ivyleemaster.core.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import de.mindmarket.ivyleemaster.ui.theme.IvyLeeMasterTheme

@Composable
fun IvyChip(
    label: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AssistChip(
        onClick = { onClick() },
        label = { Text(text = label) },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                Modifier.size(AssistChipDefaults.IconSize)
            )
        },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = MaterialTheme.colorScheme.onPrimary,
            labelColor = MaterialTheme.colorScheme.secondary,
            disabledContainerColor = MaterialTheme.colorScheme.tertiary
        )
    )
}

@Preview
@Composable
private fun IvyChipPreview() {
    IvyLeeMasterTheme {
        IvyChip("My great chip!", icon = Icons.Filled.Settings, onClick = {})
    }
}