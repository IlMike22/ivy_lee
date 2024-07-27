@file:OptIn(ExperimentalMaterial3Api::class)

package de.mindmarket.ivyleemaster.task.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.mindmarket.ivyleemaster.R
import de.mindmarket.ivyleemaster.core.presentation.GradientBackground
import de.mindmarket.ivyleemaster.core.presentation.components.IvyToolbar
import de.mindmarket.ivyleemaster.core.presentation.util.DropDownItem
import de.mindmarket.ivyleemaster.ui.theme.IvyLeeMasterTheme
import de.mindmarket.ivyleemaster.ui.theme.IvyLogo
import de.mindmarket.ivyleemaster.ui.theme.Settings
import org.koin.androidx.compose.koinViewModel

@Composable
fun TaskScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: TaskViewModel = koinViewModel()
) {
    TaskScreen(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Composable
fun TaskScreen(
    state: TaskState,
    onAction: (TaskAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = topAppBarState
    )

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
                    modifier = Modifier
                        .fillMaxSize()
                        .nestedScroll(scrollBehavior.nestedScrollConnection)
                        .padding(horizontal = 16.dp),
                    contentPadding = padding
                ) {
                    items(
                        listOf(
                            "Task1",
                            "Task2",
                            "Task3"
                        )
                    ) {
                        Text(text = "Task1")
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
            onAction = {}
        )
    }
}