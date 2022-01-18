package me.seokang.compose.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import me.seokang.compose.LoginScreen
import me.seokang.compose.ui.theme.ComposeTheme


@ExperimentalComposeUiApi
@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    ComposeTheme {
        LoginScreen()
    }
}


@Composable
fun LoginTitle() {
    Text(text = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.Black)) {
            append("Sign")
        }
        withStyle(style = SpanStyle(color = Color.Red)) {
            append(" In")
        }
    }, fontSize = 30.sp)
}

@Composable
fun LoginEmail(
    email: MutableState<TextFieldValue>,
    isError: MutableState<Boolean>,
    focusRequest: FocusRequester
) {
    OutlinedTextField(
        value = email.value,
        onValueChange = {
            if (isError.value) {
                isError.value = false
            }
            email.value = it
        },
        isError = isError.value,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Enter Email") },
        maxLines = 1,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { focusRequest.requestFocus() })
    )

    if (isError.value) {
        Text(text = "Required", color = Color.Red)
    }
}

@Composable
@ExperimentalComposeUiApi
fun LoginPassword(
    password: MutableState<TextFieldValue>,
    isError: MutableState<Boolean>,
    focusRequest: FocusRequester,
    keyboardController: SoftwareKeyboardController?,
    doneAction: () -> Unit
) {
    val passwordVisibility = remember { mutableStateOf(true) }
    OutlinedTextField(
        value = password.value,
        onValueChange = {
            if (isError.value) {
                isError.value = false
            }
            password.value = it
        },
        isError = isError.value,
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequest),
        label = {
            Text(text = "Enter Password")
        },
        trailingIcon = {
            IconButton(onClick = {
                passwordVisibility.value = !passwordVisibility.value
            }) {
                Icon(
                    imageVector = if (passwordVisibility.value) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = "visibility",
                    tint = Color.Red
                )
            }
        },
        visualTransformation = if (passwordVisibility.value) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            keyboardController?.hide()
            doneAction.invoke()
        })
    )

    if (isError.value) {
        Text(text = "Required", color = Color.Red)
    }
}
