package de.mindmarket.ivyleemaster.core.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.foundation.text2.input.TextFieldLineLimits
import androidx.compose.foundation.text2.input.TextFieldState
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IvyInputTextField(
    label: String,
    modifier: Modifier = Modifier,
    state: TextFieldState
) {
    var input by rememberSaveable {
        mutableStateOf("")
    }
    BasicTextField2(
        state = state,
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        ),
        lineLimits = TextFieldLineLimits.SingleLine,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
        modifier = Modifier.run {
            fillMaxWidth()
                .height(54.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary
                )
                .padding(16.dp)
        },
        decorator = { innerBox ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    if (state.text.isEmpty()) {
                        Text(
                            text = label,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    innerBox()
                }
            }
        }
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 16.dp)
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
private fun IvyInputTextFieldPreview() {
    IvyLeeMasterTheme {
        IvyInputTextField(
            label = "My label",
            state = TextFieldState("hello world")
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