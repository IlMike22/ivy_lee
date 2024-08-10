package de.mindmarket.ivyleemaster.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.mindmarket.ivyleemaster.core.presentation.GradientBackground
import de.mindmarket.ivyleemaster.ui.theme.IvyLeeMasterTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreenRoot(
    viewModel: SettingsViewModel = koinViewModel()
) {
    SettingsScreen(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Composable
fun SettingsScreen(
    state: SettingsState,
    onAction: (SettingsAction) -> Unit,
    modifier: Modifier = Modifier
) {
    GradientBackground {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Hello Settings Screen!")
        }
    }
}

@Preview
@Composable
private fun SettingsScreenPreview() {
    IvyLeeMasterTheme {
        SettingsScreen(state = SettingsState(), onAction = {})
    }
}