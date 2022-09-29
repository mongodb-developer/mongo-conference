@file:OptIn(ExperimentalMaterial3Api::class)

package com.mongodb.mongoize.android.screens.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mongodb.mongoize.android.MyApplicationTheme
import com.mongodb.mongoize.android.R

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


            val loginViewModel = viewModel<LoginViewModel>()

            loginViewModel.loginStatus.observeAsState(false).apply {
                if (this.value) {
                    showProgress = false
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
                label = { Text(text = "Username") },
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
                    imeAction = ImeAction.Done
                )
            )

            Button(onClick = {
                showProgress = true
                loginViewModel.doLogin(userName.value, password.value)
            }) {
                Text(text = "Login")
            }


            Text(text = "Sign up", modifier = Modifier.clickable {
                navigateToRegistration()
            })

            if (showProgress) {
                CircularProgressIndicator()
            }
        }
    }

    @Composable
    fun NavigateToHome() {
        /*val context = LocalContext.current
        context.startActivity(Intent(context, HomeScreen::class.java))*/
    }

    private fun navigateToRegistration() {
        TODO()
    }

}