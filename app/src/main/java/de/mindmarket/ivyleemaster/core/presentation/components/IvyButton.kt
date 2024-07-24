package de.mindmarket.ivyleemaster.core.presentation.components

import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.mindmarket.ivyleemaster.ui.theme.IvyLeeMasterTheme

@Composable
fun IvyPrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onClick
    ) {
        Text(text = text)
    }
}

@Composable
fun IvySecondaryButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    FilledTonalButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Text(text = text)
    }
}

@Composable
fun IvyOutlinedButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    OutlinedButton(onClick = onClick) {
        Text(text = text)
    }
}

@Preview
@Composable
private fun IvyPrimaryButtonPreview() {
    IvyLeeMasterTheme {
        IvyPrimaryButton(
            text = "Hello Ivy!",
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun IvySecondaryButtonPreview() {
    IvyLeeMasterTheme {
        IvySecondaryButton(
            text = "Hello Ivy secondary!",
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun IvyOutlinedButtonPreview() {
    IvyLeeMasterTheme {
        IvyOutlinedButton(
            text = "Hello Ivy secondary!",
            onClick = {}
        )
    }
}



