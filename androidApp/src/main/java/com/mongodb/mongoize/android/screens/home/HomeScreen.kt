@file:OptIn(ExperimentalMaterial3Api::class)

package com.mongodb.mongoize.android.screens.home


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mongodb.mongoize.android.R

class HomeScreen : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Container()
        }
    }

    @Preview
    @Composable
    fun Container() {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            fontSize = 24.sp,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = Color(0xFF3700B3),
                        titleContentColor = Color.White
                    )

                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {}) {
                    Icon(Icons.Filled.Add, "")
                }
            },
            floatingActionButtonPosition = FabPosition.End
        ) {
            ConferenceList(it)
        }
    }

    @Composable
    fun ConferenceList(paddingValues: PaddingValues) {
        LazyColumn(
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.SpaceAround

        ) {
            items(count = 100) {
                SessionItem()
            }
        }
    }

    @Preview
    @Composable
    fun SessionItem() {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(text = "My Talk title", maxLines = 2, overflow = TextOverflow.Ellipsis)

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
                text = "This talk is all about nothings\nThis talk is all about nothings\nThis talk is all about nothings\nThis talk is all about nothings\nThis talk is all about nothings\nThis talk is all about nothings\nThis talk is all about nothings\nThis talk is all about nothings\n"
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "By speakerName")
                Text(text = "Duration 180 mins")
            }

            Divider(
                thickness = DividerDefaults.Thickness,
                modifier = Modifier.padding(
                    horizontal = 4.dp,
                    vertical = 2.dp
                )
            )

        }
    }
}