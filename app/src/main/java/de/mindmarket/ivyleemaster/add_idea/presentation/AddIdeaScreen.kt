@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalFoundationApi::class
)

package de.mindmarket.ivyleemaster.add_idea.presentation

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import de.mindmarket.ivyleemaster.R
import de.mindmarket.ivyleemaster.auth.login.presentation.LoginEvent
import de.mindmarket.ivyleemaster.core.domain.model.Genre
import de.mindmarket.ivyleemaster.core.presentation.GradientBackground
import de.mindmarket.ivyleemaster.core.presentation.components.IvyChip
import de.mindmarket.ivyleemaster.core.presentation.components.IvyInputTextField
import de.mindmarket.ivyleemaster.core.presentation.components.IvyPrimaryButton
import de.mindmarket.ivyleemaster.core.presentation.components.IvySwitch
import de.mindmarket.ivyleemaster.core.presentation.components.IvyToolbar
import de.mindmarket.ivyleemaster.core.presentation.util.DropDownItem
import de.mindmarket.ivyleemaster.idea.presentation.IdeaAction
import de.mindmarket.ivyleemaster.ui.theme.IvyLeeMasterTheme
import de.mindmarket.ivyleemaster.ui.theme.IvyLogo
import de.mindmarket.ivyleemaster.ui.theme.Settings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddIdeaScreenRoot(
    viewModel: AddIdeaViewModel = koinViewModel(),
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val lifeCycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(viewModel.events, lifeCycleOwner.lifecycle) {
        lifeCycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                viewModel.events.collect { event ->
                    when (event) {
                        is AddIdeaEvent.OnAddIdeaFailed -> {
                            keyboardController?.hide()
                            Toast.makeText(
                                context,
                                "Add idea failed.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        is AddIdeaEvent.OnAddIdeaSuccess -> {
                            keyboardController?.hide()
                            Toast.makeText(
                                context,
                                context.getString(
                                    R.string.add_idea_successul_message
                                ), Toast.LENGTH_LONG
                            ).show()
                        }

                        is AddIdeaEvent.OnValidationFailed -> {
                            keyboardController?.hide()
                            Toast.makeText(
                                context,
                                context.getString(
                                    R.string.add_idea_validation_failed
                                ), Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }

    AddIdeaScreen(
        state = viewModel.state,
        onAction = viewModel::onAction,
        onBackClick = onBackClick
    )
}

@Composable
fun AddIdeaScreen(
    state: AddIdeaState,
    onAction: (IdeaAction) -> Unit,
    onBackClick: () -> Unit,
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
                    showBackButton = true,
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
                            0 -> {}
                        }
                    },
                    onBackClick = onBackClick,
                    scrollBehavior = scrollBehavior
                )
            },
            content = { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.add_idea_enter_new_idea),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier
                            .align(Alignment.Start)

                    )
                    Spacer(Modifier.height(16.dp))
                    IvyInputTextField(
                        state = state.newIdea.title,
                        label = stringResource(R.string.add_idea_hint_text_title)
                    )
                    Spacer(Modifier.height(16.dp))
                    IvyInputTextField(
                        state = state.newIdea.subtitle,
                        label = stringResource(R.string.add_idea_hint_text_subtitle)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    IvySwitch(
                        onCheckChange = { state.newIdea.isUrgent },
                        label = stringResource(R.string.add_idea_switch_high_priority_text)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    IvySwitch(
                        onCheckChange = { state.newIdea.isRepeatable },
                        label = stringResource(R.string.add_idea_switch_repeatable_text)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        IvyChip(
                            label = "Life",
                            icon = Icons.Filled.ShoppingCart,
                            onClick = { state.newIdea.copy(genre = Genre.RELATIONSHIP)})
                        Spacer(modifier = Modifier.width(16.dp))
                        IvyChip(
                            label = "Business",
                            icon = Icons.Filled.AccountBox,
                            onClick = { /*TODO*/ })
                        Spacer(modifier = Modifier.width(16.dp))
                        IvyChip(
                            label = "Health",
                            icon = Icons.Filled.Call,
                            onClick = { /*TODO*/ })
                    }
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        IvyPrimaryButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 128.dp)
                                .height(64.dp),
                            text = stringResource(R.string.add_idea_primary_button_text),
                            onClick = {
                                onAction(IdeaAction.OnAddIdeaClick)
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
private fun AddIdeaScreenPreview() {
    IvyLeeMasterTheme {
        AddIdeaScreen(
            state = AddIdeaState(),
            onAction = {},
            onBackClick = {}
        )
    }
}