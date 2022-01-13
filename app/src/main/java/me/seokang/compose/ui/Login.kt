package me.seokang.compose.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.seokang.compose.ui.theme.ComposeTheme


@ExperimentalComposeUiApi
@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    ComposeTheme {
        LoginScreen()
    }
}


@ExperimentalComposeUiApi
@Composable
fun LoginScreen() {
    val context = LocalContext.current
    val email = remember { mutableStateOf(TextFieldValue()) }
    val emailErrorState = remember { mutableStateOf(false) }
    val passwordErrorState = remember { mutableStateOf(false) }
    val password = remember { mutableStateOf(TextFieldValue()) }
    val focusRequest = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = { keyboardController?.hide() })
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.Black)) {
                append("Sign")
            }
            withStyle(style = SpanStyle(color = Color.Red)) {
                append(" In")
            }
        }, fontSize = 30.sp)

        Spacer(Modifier.size(16.dp))

        OutlinedTextField(
            value = email.value,
            onValueChange = {
                if (emailErrorState.value) {
                    emailErrorState.value = false
                }
                email.value = it
            },
            isError = emailErrorState.value,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Enter Email") },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusRequest.requestFocus() })
        )
        if (emailErrorState.value) {
            Text(text = "Required", color = Color.Red)
        }

        Spacer(Modifier.size(16.dp))

        val passwordVisibility = remember { mutableStateOf(true) }
        OutlinedTextField(
            value = password.value,
            onValueChange = {
                if (passwordErrorState.value) {
                    passwordErrorState.value = false
                }
                password.value = it
            },
            isError = passwordErrorState.value,
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
                login(context, email, emailErrorState, password, passwordErrorState)
            })
        )
        if (passwordErrorState.value) {
            Text(text = "Required", color = Color.Red)
        }
        Spacer(Modifier.size(16.dp))
        Button(
            onClick = {
                login(context, email, emailErrorState, password, passwordErrorState)
            },
            content = {
                Text(text = "Login", color = Color.White)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
        )
        Spacer(Modifier.size(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            TextButton(onClick = {

            }) {
                Text(text = "Register ?", color = Color.Red)
            }
        }
    }
}

private fun login(
    context: Context, email: MutableState<TextFieldValue>, emailErrorState: MutableState<Boolean>,
    password: MutableState<TextFieldValue>, passwordErrorState: MutableState<Boolean>
) {
    when {
        email.value.text.isEmpty() -> {
            emailErrorState.value = true
        }
        password.value.text.isEmpty() -> {
            passwordErrorState.value = true
        }
        else -> {
            passwordErrorState.value = false
            emailErrorState.value = false
            Toast.makeText(
                context,
                "Logged in successfully",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}