@file:OptIn(ExperimentalMaterial3Api::class)

package com.mongodb.mongoize.android.screens.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mongodb.mongoize.android.MyApplicationTheme
import com.mongodb.mongoize.android.R
import com.mongodb.mongoize.android.screens.home.HomeScreen
import com.mongodb.mongoize.android.screens.registration.Registration

class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Container()
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun Container() {
        MyApplicationTheme {
            Surface(modifier = Modifier.fillMaxSize()) {
                LoginView()
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun LoginView() {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            val userName = remember { mutableStateOf("") }
            val password = remember { mutableStateOf("") }
            var showProgress by remember { mutableStateOf(false) }
            val context = LocalContext.current

            val loginViewModel = viewModel<LoginViewModel>()

            loginViewModel.loginStatus.observeAsState(false).apply {
                if (this.value) {
                    showProgress = false
                    NavigateToHome()
                }
            }

            loginViewModel.alreadyLoggedIn.observeAsState(false).apply {
                if (this.value) {
                    NavigateToHome()
                }
            }

            Image(
                painter = painterResource(id = R.drawable.ic_realm_logo),
                contentScale = ContentScale.Fit,
                contentDescription = "App Logo",
                modifier = Modifier
                    .width(200.dp)
                    .defaultMinSize(minHeight = 200.dp)
                    .padding(bottom = 20.dp)
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                value = userName.value,
                onValueChange = {
                    userName.value = it
                },
                label = { Text(text = "Email") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                value = password.value,
                onValueChange = {
                    password.value = it
                },
                label = { Text(text = "Password") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Send
                ),
                visualTransformation = PasswordVisualTransformation()
            )

            Button(onClick = {
                showProgress = true
                loginViewModel.doLogin(userName.value, password.value)
            }) {
                Text(text = "Login")
            }


            Text(text = "Sign up", modifier = Modifier.clickable {
                navigateToRegistration(context)
            })

            if (showProgress) {
                CircularProgressIndicator()
            }
        }
    }

    @Composable
    fun NavigateToHome() {
        val context = LocalContext.current
        context.startActivity(Intent(context, HomeScreen::class.java))
    }

    private fun navigateToRegistration(context: Context) {
        context.startActivity(Intent(context, Registration::class.java))
    }

}