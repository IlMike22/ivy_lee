@file:OptIn(ExperimentalMaterial3Api::class)

package de.mindmarket.ivyleemaster.task.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.mindmarket.ivyleemaster.R
import de.mindmarket.ivyleemaster.core.presentation.GradientBackground
import de.mindmarket.ivyleemaster.core.presentation.components.IvyToolbar
import de.mindmarket.ivyleemaster.core.presentation.util.DropDownItem
import de.mindmarket.ivyleemaster.ui.theme.EyesOpen
import de.mindmarket.ivyleemaster.ui.theme.IvyLeeMasterTheme
import de.mindmarket.ivyleemaster.ui.theme.IvyLogo
import de.mindmarket.ivyleemaster.ui.theme.Settings

@Composable
fun TaskScreenRoot(
    state: TaskState,
    onAction: (TaskAction) -> Unit,
    isRefreshUi: Boolean
) {
    TaskScreen(
        state = state,
        onAction = onAction,
        isRefreshUi = isRefreshUi
    )
}

@Composable
fun TaskScreen(
    state: TaskState,
    onAction: (TaskAction) -> Unit,
    isRefreshUi: Boolean,
    modifier: Modifier = Modifier
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = topAppBarState
    )

    if (isRefreshUi) {
        onAction(TaskAction.OnRefresh)
    } else {
        onAction(TaskAction.ResetRefresh)
    }

    GradientBackground {
        Scaffold(
            topBar = {
                IvyToolbar(
                    showBackButton = false,
                    title = stringResource(R.string.ivy_lee_title),
                    menuItems = listOf(
                        DropDownItem(
                            Settings,
                            stringResource(R.string.settings)
                        )
                    ),
                    startContent = {
                        Icon(
                            imageVector = IvyLogo,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(30.dp)
                        )
                    },
                    onMenuItemClick = { index ->
                        when (index) {
                            0 -> onAction(TaskAction.OnSettingsClick)
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            },
            content = { padding ->
                if (state.isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(32.dp)
                        )
                    }
                } else if (state.isError) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Text(
                            text = stringResource(R.string.ivy_lee_task_loading_error_text),
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.padding(padding)
                    ) {
                        items(state.tasks) { task ->
                            ListItem(
                                headlineContent = {
                                    Text(text = task.title)
                                },
                                supportingContent = {
                                    Text(text = task.description)
                                },
                                leadingContent = {
                                    Icon(
                                        imageVector = EyesOpen,
                                        contentDescription = null
                                    )
                                }
                            )
                        }
                    }
                }
            }
        )
    }
}

@Preview
@Composable
private fun TaskScreenPreview() {
    IvyLeeMasterTheme {
        TaskScreen(
            state = TaskState(
                username = "Michael",
                tasks = emptyList(),
            ),
            onAction = {},
            isRefreshUi = false
        )
    }
}