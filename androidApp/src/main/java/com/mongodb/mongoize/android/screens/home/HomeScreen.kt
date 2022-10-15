@file:OptIn(ExperimentalMaterial3Api::class)

package com.mongodb.mongoize.android.screens.home


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mongodb.mongoize.ConferenceInfo
import com.mongodb.mongoize.android.R
import com.mongodb.mongoize.android.screens.addconference.AddConferenceActivity

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
        val context = LocalContext.current

        Scaffold(topBar = {
            CenterAlignedTopAppBar(
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
        }, floatingActionButton = {
            FloatingActionButton(onClick = {
                startActivity(Intent(context, AddConferenceActivity::class.java))
            }) {
                Icon(Icons.Filled.Add, "")
            }
        }, floatingActionButtonPosition = FabPosition.End
        ) {
            ConferenceList(it.calculateTopPadding())
        }
    }

    @Composable
    fun ConferenceList(topPaddingValue: Dp) {

        val homeVM = viewModel<HomeViewModel>()
        val events = homeVM.events.observeAsState(emptyList()).value

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = topPaddingValue, start = 8.dp, end = 8.dp),
            verticalArrangement = Arrangement.SpaceAround

        ) {

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Live Events", fontSize = 20.sp, fontWeight = FontWeight.Medium
                    )
                }

                Divider(
                    thickness = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp)
                        .padding(bottom = 16.dp)
                )
            }

            items(count = events.size) {
                EventItem(events[it])
            }
        }
    }

    @Composable
    fun EventItem(conferenceInfo: ConferenceInfo) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            shape = RoundedCornerShape(4.dp),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)

        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = CenterVertically

            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 8.dp),
                        text = "${conferenceInfo.name}, ${conferenceInfo.location}",
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = conferenceInfo.startDate,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
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