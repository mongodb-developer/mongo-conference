package com.mongodb.mongoize.android.screens.conferenceDetail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mongodb.mongoize.SessionInfo
import com.mongodb.mongoize.android.MyApplicationTheme
import io.realm.kotlin.types.ObjectId

@ExperimentalMaterial3Api
class ConferenceDetailView : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                TopBar()
            }
        }
    }

    @Composable
    fun TopBar() {
        val name = intent.getStringExtra("name") ?: return
        val confId = intent.getStringExtra("id") ?: return

        val vm = viewModel<ConferenceDetailViewModel>()
        vm.updateConferenceId(confId)

        Scaffold(topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = name,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }, colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color(0xFF3700B3), titleContentColor = Color.White
                )
            )
        }) {
            Container(it.calculateTopPadding(), vm)
        }
    }

    @Composable
    fun Container(topPadding: Dp, vm: ConferenceDetailViewModel) {
        val allTalks = vm.talks.observeAsState(emptyList())
        val selectedTalks = vm.selectedTalks.observeAsState(emptyList())
        val onTalkStateChange = { talkId: ObjectId, state: Boolean ->
            vm.updateTalkStatus(talkId = talkId, state = state)
        }


        LazyColumn(modifier = Modifier.padding(top = topPadding)) {
            item {
                Text(text = "Selected Talks")
            }

            items(count = selectedTalks.value.size) {
                TalkView(talk = allTalks.value[it], onTalkStateChange)
            }

            item {
                Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)
            }

            item {
                Text(text = "All Talks")
            }

            items(count = allTalks.value.size) {
                TalkView(talk = allTalks.value[it], onTalkStateChange = onTalkStateChange)
            }

        }
    }

    @Composable
    fun TalkView(talk: SessionInfo, onTalkStateChange: (ObjectId, Boolean) -> Unit) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.7f)
            ) {

                Text(text = talk.talkTitle, modifier = Modifier.fillMaxWidth())

                Text(text = talk.abstract, maxLines = 2, modifier = Modifier.fillMaxWidth())


                Text(text = "${talk.duration} mins", modifier = Modifier.fillMaxWidth())
            }

            Switch(checked = talk.isAccepted, onCheckedChange = {
                onTalkStateChange(talk._id, it)
            })
        }
    }

}

