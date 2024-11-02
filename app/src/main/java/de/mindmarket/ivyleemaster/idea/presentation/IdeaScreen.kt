package de.mindmarket.ivyleemaster.idea.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import de.mindmarket.ivyleemaster.core.presentation.components.IvyIdeaItem
import de.mindmarket.ivyleemaster.ui.theme.IvyLeeMasterTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun IdeaScreenRoot(
    state: IdeaState,
    onAction: (IdeaAction) -> Unit,
    viewModel: IdeaViewModel = koinViewModel(),
    onAddIdeaClick: () -> Unit
) {
    IdeaScreen(
        state = state,
        onAction = viewModel::onAction,
        onAddIdeaClick = onAddIdeaClick
    )
}

@OptIn(ExperimentalFoundationApi::class)
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
                    }
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()

            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    items(state.ideas) { idea ->
                       IvyIdeaItem(idea)
                        Spacer(Modifier.height(4.dp))
                    }
                }
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