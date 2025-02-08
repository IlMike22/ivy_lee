package de.mindmarket.ivyleemaster.idea.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import de.mindmarket.ivyleemaster.R
import de.mindmarket.ivyleemaster.core.presentation.GradientBackground
import de.mindmarket.ivyleemaster.core.presentation.components.IvyFloatingActionButton
import de.mindmarket.ivyleemaster.core.presentation.components.IvyIdeaItem
import de.mindmarket.ivyleemaster.ui.theme.IvyLeeMasterTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun IdeaScreenRoot(
    state: IdeaState,
    onAction: (IdeaAction) -> Unit,
    onAddIdeaClick: () -> Unit,
    invalidateList: LiveData<Boolean>? = null
) {
    IdeaScreen(
        state = state,
        onAction = onAction,
        onAddIdeaClick = onAddIdeaClick,
        invalidateList = invalidateList?.value ?: false
    )
}

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
                        items(
                            items = state.ideas,
                            key = { it.id }
                        ) { idea ->
                            SwipeBox(
                                onDelete = {
                                    onAction(IdeaAction.OnIdeaDeleteClick(idea.id))
                                },
                                onEdit = {
                                    onAction(IdeaAction.OnIdeaEditClick(idea))
                                },
                                modifier = Modifier.animateItem(
                                    fadeInSpec = null,
                                    fadeOutSpec = null
                                )
                                    .padding(8.dp)
                            ) {
                                IvyIdeaItem(idea = idea, onAction = onAction)
                                Spacer(Modifier.height(4.dp))
                            }

                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeBox(
    modifier: Modifier = Modifier,
    onDelete: () -> Unit,
    onEdit: () -> Unit,
    content: @Composable () -> Unit
) {
    val swipeState = rememberSwipeToDismissBoxState()

    lateinit var icon: ImageVector
    lateinit var alignment: Alignment
    val color: Color

    when (swipeState.dismissDirection) {
        SwipeToDismissBoxValue.StartToEnd -> {
            icon = Icons.Outlined.Edit
            alignment = Alignment.CenterStart
            color = Color.Green.copy(alpha = 0.3f)
        }

        SwipeToDismissBoxValue.EndToStart -> {
            icon = Icons.Outlined.Delete
            alignment = Alignment.CenterEnd
            color = MaterialTheme.colorScheme.errorContainer
        }

        SwipeToDismissBoxValue.Settled -> {
            icon = Icons.Outlined.Delete
            alignment = Alignment.CenterEnd
            color = MaterialTheme.colorScheme.surfaceContainer
        }
    }

    SwipeToDismissBox(
        modifier = modifier.animateContentSize(),
        state = swipeState,
        backgroundContent = {
            Box(
                contentAlignment = alignment,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color)
            ) {
                Icon(
                    imageVector = icon, contentDescription = null,
                    modifier = Modifier.minimumInteractiveComponentSize()
                )
            }
        }
    ) {
        content()
    }

    when (swipeState.currentValue) {
        SwipeToDismissBoxValue.StartToEnd -> {
            LaunchedEffect(swipeState) {
                onEdit()
                swipeState.snapTo(SwipeToDismissBoxValue.Settled)
            }
        }

        SwipeToDismissBoxValue.EndToStart -> {
            onDelete()
        }

        SwipeToDismissBoxValue.Settled -> Unit
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