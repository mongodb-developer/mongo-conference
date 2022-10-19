@file:OptIn(ExperimentalMaterial3Api::class)

package com.mongodb.mongoize.android.screens.addconference

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mongodb.mongoize.android.MyApplicationTheme
import com.mongodb.mongoize.android.R

class AddConferenceActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                View()
            }
        }
    }

    @Preview
    @Composable
    fun View() {
        Scaffold(topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.conference_activity),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }, colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color(0xFF3700B3), titleContentColor = Color.White
                )
            )
        }) {
            ContentView(topPadding = it.calculateTopPadding())
        }
    }


    @Composable
    fun ContentView(topPadding: Dp) {

        val vm = viewModel<AddConferenceViewModel>()

        val name = remember { mutableStateOf("") }
        val location = remember { mutableStateOf<String>("") }
        val startDate = remember { mutableStateOf<String>("") }
        val endDate = remember { mutableStateOf<String>("") }

        val onValueChange = { type: String, value: String ->
            when (type) {
                "conference" -> name.value = value
                "location" -> location.value = value
                "startDate" -> startDate.value = value
                "endDate" -> endDate.value = value
            }
        }


        Column(
            modifier = Modifier.padding(
                top = topPadding,
                start = 8.dp,
                end = 8.dp
            )
        ) {
            ConferenceName(initialValue = name.value, onValueChange = onValueChange)
            Location(initialValue = location.value, onValueChange = onValueChange)
            StartDate(initialValue = startDate.value, onValueChange = onValueChange)
            EndDate(initialValue = endDate.value, onValueChange = onValueChange)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                OutlinedButton(
                    colors = ButtonDefaults.buttonColors(),
                    content = {
                        Text(
                            text = stringResource(id = R.string.conference_save),
                            modifier = Modifier.padding(horizontal = 8.dp),
                        )
                    },
                    onClick = {
                        vm.addConference(name.value, location.value, startDate.value, endDate.value)
                        finish()
                    })
            }
        }
    }

    @Composable
    fun ConferenceName(
        initialValue: String, onValueChange: (String, String) -> Unit
    ) {
        TextField(
            value = initialValue,
            onValueChange = {
                onValueChange("conference", it)
            },
            label = { Text(text = "Conference name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
        )
    }

    @Composable
    fun Location(
        initialValue: String, onValueChange: (String, String) -> Unit
    ) {
        TextField(
            value = initialValue,
            onValueChange = {
                onValueChange("location", it)
            },
            label = { Text(text = "Location") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
        )
    }


    @Composable
    fun StartDate(
        initialValue: String, onValueChange: (String, String) -> Unit
    ) {
        TextField(
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            value = initialValue,
            onValueChange = {
                if (it.length <= 10) onValueChange("startDate", it)
            },
            label = { Text(text = "Start Date") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            placeholder = { Text(text = "DD-MM-YYYY Format")}
        )
    }

    @Composable
    fun EndDate(
        initialValue: String, onValueChange: (String, String) -> Unit
    ) {
        TextField(
            singleLine = true,
            value = initialValue,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = {
                if (it.length <= 10) onValueChange("endDate", it)
            },
            label = { Text(text = "End Date") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            placeholder = { Text(text = "DD-MM-YYYY Format")}
        )
    }


}