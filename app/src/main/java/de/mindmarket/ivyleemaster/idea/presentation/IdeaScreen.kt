package de.mindmarket.ivyleemaster.idea.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.mindmarket.ivyleemaster.core.presentation.GradientBackground
import de.mindmarket.ivyleemaster.core.presentation.components.IvyFloatingActionButton
import de.mindmarket.ivyleemaster.ui.theme.IvyLeeMasterTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun IdeaScreenRoot(
    viewModel: IdeaViewModel = koinViewModel(),
    onAddIdeaClick: () -> Unit
) {
    IdeaScreen(
        state = viewModel.state,
        onAction = viewModel::onAction,
        onAddIdeaClick = onAddIdeaClick
    )
}

@Composable
fun IdeaScreen(
    state: IdeaState,
    onAction: (IdeaAction) -> Unit,
    onAddIdeaClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    GradientBackground {
        Scaffold(
            floatingActionButton = {
                IvyFloatingActionButton(
                    icon = Icons.Filled.Add,
                    onClick = {
                        onAddIdeaClick()
                    },
                    modifier = Modifier.padding(bottom = 80.dp)
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                Text(
                    text = "Hello Idea Screen!",
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview
@Composable
private fun IdeaScreenPreview() {
    IvyLeeMasterTheme {
        IdeaScreen(
            state = IdeaState(),
            onAction = {},
            onAddIdeaClick = {}
        )
    }
}