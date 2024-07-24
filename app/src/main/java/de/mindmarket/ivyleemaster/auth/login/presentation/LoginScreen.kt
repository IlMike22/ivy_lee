package de.mindmarket.ivyleemaster.auth.login.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
        onAction = viewModel::onAction,
        onRegisterClick = onRegisterClick
    )
}

@Composable
private fun LoginScreen(
    modifier: Modifier = Modifier,
    state: LoginState,
    onRegisterClick: () -> Unit,
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
                text = stringResource(id = R.string.login),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                IvyPrimaryButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    text = stringResource(R.string.login),
                    onClick = {
                        onAction(LoginAction.OnLoginClick)
                    }
                )
                Spacer(Modifier.width(16.dp))
                IvySecondaryButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    text = stringResource(R.string.register),
                    onClick = {
                        onRegisterClick()
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Text(
                    text = stringResource(R.string.forgot_password),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.width(4.dp))
                ClickableText(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(MaterialTheme.colorScheme.secondary)) {
                            append(stringResource(id = R.string.click_here))
                        }
                    },
                    onClick = { onAction(LoginAction.OnForgetPasswordClick) }
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
            onAction = {},
            onRegisterClick = {}
        )
    }
}