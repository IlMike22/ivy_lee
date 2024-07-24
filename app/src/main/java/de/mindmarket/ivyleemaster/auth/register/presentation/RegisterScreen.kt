package de.mindmarket.ivyleemaster.auth.register.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.mindmarket.ivyleemaster.R
import de.mindmarket.ivyleemaster.core.presentation.GradientBackground
import de.mindmarket.ivyleemaster.core.presentation.components.IvyInputTextField
import de.mindmarket.ivyleemaster.core.presentation.components.IvyPrimaryButton
import de.mindmarket.ivyleemaster.core.presentation.components.IvySecondaryButton
import de.mindmarket.ivyleemaster.ui.theme.IvyLeeMasterTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreenRoot(
    viewModel: RegisterViewModel = koinViewModel(),
    onRegisterClick: () -> Unit,
    onNavigateToLoginClick: () -> Unit
) {
    RegisterScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                RegisterAction.OnNavigateToLoginClick -> onNavigateToLoginClick()
                RegisterAction.OnRegisterClick -> onRegisterClick()
                else -> viewModel.onAction(action)
            }
        }
    )
}

@Composable
fun RegisterScreen(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit
) {
    GradientBackground {
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxSize()
        ) {
            Text(
                modifier = Modifier.padding(top = 64.dp),
                text = stringResource(id = R.string.register),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.register_description),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            IvyInputTextField(label = stringResource(R.string.your_email))
            Spacer(modifier = Modifier.height(16.dp))
            IvyInputTextField(label = stringResource(R.string.your_password))
            Spacer(modifier = Modifier.height(16.dp))
            IvyInputTextField(label = stringResource(R.string.repeat_password))
            Spacer(modifier = Modifier.height(28.dp))
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                IvyPrimaryButton(
                    text = stringResource(id = R.string.register),
                    onClick = { onAction(RegisterAction.OnRegisterClick) }
                )
                Spacer(modifier = Modifier.width(16.dp))
                IvySecondaryButton(
                    text = stringResource(R.string.go_to_login),
                    onClick = { onAction(RegisterAction.OnNavigateToLoginClick) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun RegisterScreenPreview() {
    IvyLeeMasterTheme {
        RegisterScreen(
            state = RegisterState(),
            onAction = {}
        )
    }
}