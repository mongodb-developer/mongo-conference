@file:OptIn(ExperimentalMaterial3Api::class)

package com.mongodb.mongoize.android.screens.profile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mongodb.mongoize.android.MyApplicationTheme
import com.mongodb.mongoize.android.R

class ProfileScreen : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Container()
            }
        }
    }

    @Preview
    @Composable
    fun Container() {

        val isReadOnly = remember { mutableStateOf(false) }
        val profileVM: ProfileViewModel = viewModel()

        val initialValueName = remember { mutableStateOf("") }
        val initialValueEmail = remember { mutableStateOf("") }
        val initialValueOrganization = remember { mutableStateOf<String>("") }
        val initialValuePhone = remember { mutableStateOf<String>("") }

        val onValueChange = { type: String, value: String ->
            when (type) {
                "name" -> initialValueEmail.value = value
                "orgName" -> initialValueOrganization.value = value
                "phoneNumber" -> initialValuePhone.value = value
            }

        }

        profileVM.userInfo.observeAsState().apply {
            this.value?.let {
                initialValueName.value = it.name
                initialValueEmail.value = it.email
                initialValueOrganization.value = it.orgName ?: ""
                initialValuePhone.value = it.phoneNumber?.toString() ?: ""
            }
        }


        Scaffold(topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        fontSize = 24.sp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }, colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color(0xFF3700B3), titleContentColor = Color.White
                )

            )
        }) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White)
                    .padding(it)
            ) {
                UserImage()

                UserName(
                    isReadOnly = isReadOnly.value,
                    initialValue = initialValueName.value,
                    onValueChange = onValueChange
                )

                Email(initialValue = initialValueEmail.value)

                OrganizationName(
                    isReadOnly = isReadOnly.value,
                    initialValue = initialValueOrganization.value,
                    onValueChange = onValueChange
                )

                PhoneNumber(
                    isReadOnly = isReadOnly.value,
                    initialValue = initialValuePhone.value,
                    onValueChange = onValueChange
                )
            }
        }

    }

    @Preview
    @Composable
    fun UserImage() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 36.dp, bottom = 36.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_profile),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.Transparent, CircleShape)
                    .align(CenterHorizontally),
                contentScale = ContentScale.Crop
            )
        }
    }

    @Composable
    fun UserName(
        isReadOnly: Boolean, initialValue: String, onValueChange: (String, String) -> Unit
    ) {
        TextField(
            value = initialValue,
            onValueChange = {
                onValueChange("name", it)
            },
            label = { Text(text = "Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            readOnly = isReadOnly
        )
    }

    @Composable
    fun Email(initialValue: String) {
        TextField(
            value = initialValue,
            onValueChange = {},
            label = { Text(text = "Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            readOnly = true
        )
    }

    @Composable
    fun OrganizationName(
        isReadOnly: Boolean, initialValue: String, onValueChange: (String, String) -> Unit
    ) {
        TextField(
            value = initialValue,
            onValueChange = { onValueChange("orgName", it) },
            label = { Text(text = "Organization Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            readOnly = isReadOnly
        )
    }

    @Composable
    fun PhoneNumber(
        isReadOnly: Boolean, initialValue: String, onValueChange: (String, String) -> Unit
    ) {
        TextField(
            value = initialValue,
            onValueChange = { onValueChange("phoneNumber", it) },
            label = { Text(text = "Phone number") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            readOnly = isReadOnly
        )
    }
}