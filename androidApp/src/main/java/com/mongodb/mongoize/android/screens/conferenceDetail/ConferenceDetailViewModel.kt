package com.mongodb.mongoize.android.screens.conferenceDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.mongodb.mongoize.RealmRepo
import com.mongodb.mongoize.SessionInfo
import io.realm.kotlin.types.ObjectId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ConferenceDetailViewModel() : ViewModel() {

    val repo = RealmRepo()
    lateinit var conferenceId: ObjectId

    fun updateConferenceId(id: String) {
        conferenceId = ObjectId.from(id)
    }

    val talks: LiveData<List<SessionInfo>> = liveData {
        emitSource(repo.getTalks(conferenceId).asLiveData(Dispatchers.IO))
    }

    val selectedTalks: LiveData<List<SessionInfo>> = liveData {
        emitSource(repo.getSelectedTalks(conferenceId).asLiveData(Dispatchers.IO))
    }

    fun updateTalkStatus(talkId: ObjectId, state: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateTalkState(talkId, state)
        }
    }
}