package de.mindmarket.ivyleemaster.auth.login.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.mindmarket.ivyleemaster.IvyLeeMasterApp
import de.mindmarket.ivyleemaster.R
import de.mindmarket.ivyleemaster.core.presentation.GradientBackground
import de.mindmarket.ivyleemaster.core.presentation.components.IvyInputTextField
import de.mindmarket.ivyleemaster.core.presentation.components.IvyPasswordTextField
import de.mindmarket.ivyleemaster.core.presentation.components.IvyPrimaryButton
import de.mindmarket.ivyleemaster.core.presentation.components.IvySecondaryButton
import de.mindmarket.ivyleemaster.ui.theme.IvyLeeMasterTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = koinViewModel(),
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit
) {

    LoginScreen(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun LoginScreen(
    modifier: Modifier = Modifier,
    state: LoginState,
    onAction: (LoginAction) -> Unit
) {
    GradientBackground {
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxSize()
        ) {
            Text(
                modifier = Modifier.padding(top = 64.dp),
                text = stringResource(R.string.welcome_ivy),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.height(16.dp))
            IvyInputTextField(
                label = stringResource(R.string.type_user_name)
            )
            Spacer(modifier = Modifier.height(16.dp))
            IvyPasswordTextField()
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                IvyPrimaryButton(
                    text = stringResource(R.string.login),
                    onClick = {
                        onAction(LoginAction.OnLoginClick)
                    }
                )
                Spacer(Modifier.width(16.dp))
                IvySecondaryButton(
                    text = stringResource(R.string.register),
                    onClick = {
                        onAction(LoginAction.OnRegisterClick)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    IvyLeeMasterTheme {
        LoginScreen(
            state = LoginState(
                isError = false,
                isPasswordVisible = false
            ),
            onAction = {}
        )
    }
}