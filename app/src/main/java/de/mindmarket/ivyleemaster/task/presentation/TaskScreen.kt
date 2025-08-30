@file:OptIn(ExperimentalMaterial3Api::class)

package de.mindmarket.ivyleemaster.task.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getString
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import de.mindmarket.ivyleemaster.R
import de.mindmarket.ivyleemaster.core.presentation.GradientBackground
import de.mindmarket.ivyleemaster.core.presentation.components.IvyTaskItem
import de.mindmarket.ivyleemaster.core.presentation.components.IvyToolbar
import de.mindmarket.ivyleemaster.core.presentation.util.DropDownItem
import de.mindmarket.ivyleemaster.idea.presentation.SwipeBox
import de.mindmarket.ivyleemaster.ui.theme.IvyLeeMasterTheme
import de.mindmarket.ivyleemaster.ui.theme.IvyLogo
import de.mindmarket.ivyleemaster.ui.theme.Settings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.koinViewModel

@Composable
fun TaskScreenRoot(
    viewModel: TaskViewModel = koinViewModel(),
    isRefreshUi: Boolean
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val lifeCycleOwner = LocalLifecycleOwner.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    LaunchedEffect(viewModel.events, lifeCycleOwner.lifecycle) {
        lifeCycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            withContext(Dispatchers.Main.immediate) {
                viewModel.events.collect { event ->
                    when (event) {
                        is TaskEvent.OnShowDeleteTaskMessage -> {
                            Toast.makeText(
                                context,
                                getString(context, event.message),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    TaskScreen(
        state = state,
        onAction = viewModel::onAction,
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
                    scrollBehavior = scrollBehavior,
                    modifier = modifier
                )
            },
            content = { innerPadding ->
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
                            text = stringResource(R.string.task_loading_error_text),
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                } else if (state.tasks.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Text(
                            text = stringResource(R.string.task_empty_task_list),
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                        ) {
                            items(
                                items = state.tasks,
                                key = { it.id }
                            ) { task ->
                                SwipeBox(
                                    onDelete = {
                                        onAction(TaskAction.OnTaskDeleteClick(task.id))
                                    },
                                    onEdit = {

                                    },
                                    modifier = Modifier
                                        .animateItem(
                                            fadeInSpec = null,
                                            fadeOutSpec = null
                                        )
                                        .padding(8.dp)
                                ) {
                                    IvyTaskItem(
                                        task = task,
                                        onAction = {
                                            onAction(TaskAction.OnTaskCompleteClick(task.id))
                                        }
                                    )

                                    Spacer(Modifier.height(4.dp))
                                }
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