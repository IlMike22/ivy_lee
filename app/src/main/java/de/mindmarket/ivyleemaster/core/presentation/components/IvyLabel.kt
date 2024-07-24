package de.mindmarket.ivyleemaster.core.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun IvyLabel(
    text:String,
    modifier: Modifier = Modifier,
    isClickable: Boolean = false
) {
    var labelText by rememberSaveable {
        mutableStateOf(text)
    }



}