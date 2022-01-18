package me.seokang.compose

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import me.seokang.compose.ui.LoginEmail
import me.seokang.compose.ui.LoginPassword
import me.seokang.compose.ui.LoginTitle
import me.seokang.compose.ui.theme.ComposeTheme

class MainActivity : ComponentActivity() {

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeTheme {
                LoginScreen()
            }
        }
    }
}


@Composable
@ExperimentalComposeUiApi
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

        LoginTitle()

        Spacer(Modifier.size(16.dp))
        LoginEmail(email, emailErrorState, focusRequest)
        Spacer(Modifier.size(16.dp))
        LoginPassword(password, passwordErrorState, focusRequest, keyboardController) {
            login(context, email, emailErrorState, password, passwordErrorState)
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