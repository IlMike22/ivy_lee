package de.mindmarket.ivyleemaster.auth.register.presentation

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.runtime.LaunchedEffect
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
import de.mindmarket.ivyleemaster.core.presentation.GradientBackground
import de.mindmarket.ivyleemaster.core.presentation.components.IvyInputTextField
import de.mindmarket.ivyleemaster.core.presentation.components.IvyPasswordTextField
import de.mindmarket.ivyleemaster.core.presentation.components.IvyPrimaryButton
import de.mindmarket.ivyleemaster.core.presentation.components.IvySecondaryButton
import de.mindmarket.ivyleemaster.ui.theme.IvyLeeMasterTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreenRoot(
    viewModel: RegisterViewModel = koinViewModel(),
    onNavigateToLoginClick: () -> Unit
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val lifeCycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(viewModel.events, lifeCycleOwner.lifecycle) {
        lifeCycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                viewModel.events.collect { event ->
                    when (event) {
                        is RegisterEvent.OnRegisterFailed -> {
                            keyboardController?.hide()
                            Toast.makeText(
                                context,
                                event.error.asString(context),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        RegisterEvent.OnRegisterSuccess -> Toast.makeText(
                            context,
                            context.getString(
                                R.string.registration_success_message
                            ), Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    RegisterScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                RegisterAction.OnNavigateToLoginClick -> onNavigateToLoginClick()
                RegisterAction.OnRegisterClick -> viewModel.onAction(RegisterAction.OnRegisterClick)
                else -> viewModel.onAction(action)
            }
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RegisterScreen(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit
) {
    GradientBackground {
        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
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
            IvyInputTextField(
                state = state.email,
                hint = stringResource(R.string.your_email)
            )
            Spacer(modifier = Modifier.height(16.dp))
            IvyPasswordTextField(
                state = state.password,
                onTogglePasswordVisibility = {
                    onAction(RegisterAction.OnTogglePasswordVisibilityClick)
                },
                isPasswordVisible = state.isPasswordVisible,
                hint = stringResource(R.string.your_password)
            )
            Spacer(modifier = Modifier.height(16.dp))
            IvyInputTextField(
                state = state.repeatPassword,
                hint = stringResource(R.string.repeat_password)
            )
            Spacer(modifier = Modifier.height(28.dp))
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                IvyPrimaryButton(
                    text = stringResource(id = R.string.register),
                    onClick = { onAction(RegisterAction.OnRegisterClick) },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(16.dp))
                IvySecondaryButton(
                    text = stringResource(R.string.go_to_login),
                    onClick = { onAction(RegisterAction.OnNavigateToLoginClick) },
                    modifier = Modifier.weight(1f)
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