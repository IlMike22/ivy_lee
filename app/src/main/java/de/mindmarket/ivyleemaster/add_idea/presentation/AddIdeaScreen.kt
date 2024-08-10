@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)

package de.mindmarket.ivyleemaster.add_idea.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import de.mindmarket.ivyleemaster.core.presentation.components.IvyInputTextField
import de.mindmarket.ivyleemaster.core.presentation.components.IvyToolbar
import de.mindmarket.ivyleemaster.core.presentation.util.DropDownItem
import de.mindmarket.ivyleemaster.idea.presentation.IdeaAction
import de.mindmarket.ivyleemaster.ui.theme.IvyLeeMasterTheme
import de.mindmarket.ivyleemaster.ui.theme.IvyLogo
import de.mindmarket.ivyleemaster.ui.theme.Settings
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddIdeaScreenRoot(
    viewModel: AddIdeaViewModel = koinViewModel(),
    onBackClick: () -> Unit
) {
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
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier.padding(top = 64.dp),
                        text = stringResource(R.string.add_idea_enter_new_idea),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.labelMedium
                    )
                    Spacer(Modifier.height(16.dp))
                    IvyInputTextField(
                        state = state.newIdea.title,
                        label = stringResource(R.string.add_idea_hint_text_title)
                    )
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