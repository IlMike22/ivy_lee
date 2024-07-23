package de.mindmarket.ivyleemaster.core.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.mindmarket.ivyleemaster.R
import de.mindmarket.ivyleemaster.ui.theme.IvyLeeMasterTheme

@Composable
fun IvyInputTextField(
    label: String,
    modifier: Modifier = Modifier
) {
    var input by rememberSaveable {
        mutableStateOf("")
    }

    OutlinedTextField(
        value = input,
        onValueChange = {
            input = it
        },
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        ),
        singleLine = true,
        label = { Text(text = label) },
        modifier = modifier
            .padding(16.dp)
    )
}

@Composable
fun IvyPasswordTextField(
    modifier: Modifier = Modifier
) {
    var input by rememberSaveable {
        mutableStateOf("")
    }
    var showPassword by rememberSaveable {
        mutableStateOf(false)
    }
    val passwordVisualTransformation = remember {
        PasswordVisualTransformation()
    }

    OutlinedTextField(
        value = input,
        onValueChange = {
            input = it
        },
        visualTransformation = if (showPassword) VisualTransformation.None else passwordVisualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        ),
        singleLine = true,
        trailingIcon = {
            Icon(
                painter = if (showPassword) painterResource(R.drawable.password_visibility)
                else painterResource(R.drawable.password_visibility_off),
                contentDescription = stringResource(R.string.toggle_password_visibility),
                modifier = Modifier.clickable {
                    showPassword = !showPassword
                }
            )
        },
        label = { Text(text = stringResource(R.string.your_password)) },
        modifier = modifier
            .padding(16.dp)
    )
}

@Preview
@Composable
private fun IvyInputTextFieldPreview() {
    IvyLeeMasterTheme {
        IvyInputTextField(
            label = "My label"
        )
    }
}

@Preview
@Composable
private fun IvyPasswordTextFieldPreview() {
    IvyLeeMasterTheme {
        IvyPasswordTextField()
    }
}