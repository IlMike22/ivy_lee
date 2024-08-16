package de.mindmarket.ivyleemaster.core.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.mindmarket.ivyleemaster.ui.theme.IvyLeeMasterTheme

@Composable
fun IvySwitch(
    onCheckChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null
) {
    var checked by rememberSaveable {
        mutableStateOf(true)
    }
    Row(
        modifier = modifier
    ) {
        Switch(
            checked = checked, onCheckedChange = {
                checked = !checked
                onCheckChange(checked)
            },
            thumbContent = if (checked) {
                {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        modifier = Modifier.size(SwitchDefaults.IconSize),
                    )
                }
            } else {
                null
            }
        )
        if (label != null) {
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = label, modifier = Modifier
                    .padding(top = 16.dp),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview
@Composable
private fun IvySwitchPreview() {
    IvyLeeMasterTheme {
        IvySwitch(onCheckChange = {}, label = "This is a label!")
    }
}
