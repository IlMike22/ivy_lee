package de.mindmarket.ivyleemaster.idea.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import de.mindmarket.ivyleemaster.R
import de.mindmarket.ivyleemaster.add_idea.presentation.AddIdeaAction
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
    onAddIdeaClick: () -> Unit,
    invalidateList: LiveData<Boolean>? = null
) {
    IdeaScreen(
        state = state,
        onAction = viewModel::onAction,
        onAddIdeaClick = onAddIdeaClick,
        invalidateList = invalidateList?.value?:false
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IdeaScreen(
    state: IdeaState,
    onAction: (IdeaAction) -> Unit,
    onAddIdeaClick: () -> Unit,
    modifier: Modifier = Modifier,
    invalidateList: Boolean
) {

    if (invalidateList) {
        onAction(IdeaAction.OnInvalidateList)
    }

    GradientBackground {
        Scaffold(
            floatingActionButton = {
                IvyFloatingActionButton(
                    icon = Icons.Filled.Add,
                    onClick = {
                        onAddIdeaClick()
                    },
                    isVisible = !state.isError
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()

            ) {
                if (state.isError) {
                    Text(text = stringResource(R.string.idea_error_loading_ideas))
                } else {
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
}

@Preview
@Composable
private fun IdeaScreenPreview() {
    IvyLeeMasterTheme {
        IdeaScreen(
            state = IdeaState(),
            onAction = {},
            onAddIdeaClick = {},
            invalidateList = false
        )
    }
}