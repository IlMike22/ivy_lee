@file:OptIn(ExperimentalMaterial3Api::class)

package de.mindmarket.ivyleemaster.task.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
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
    isRefresh: Boolean = false
) {
    TaskScreen(
        state = state,
        onAction = onAction,
        isRefresh = isRefresh,
    )
}

@Composable
fun TaskScreen(
    state: TaskState,
    onAction: (TaskAction) -> Unit,
    isRefresh: Boolean,
    modifier: Modifier = Modifier
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = topAppBarState
    )

    if (isRefresh) {
        println("!! refresh action triggered in TaskScreen!")
        onAction(TaskAction.OnRefresh)
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
            isRefresh = false
        )
    }
}