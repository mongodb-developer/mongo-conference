package com.mongodb.mongoize.android.screens.conferenceDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
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
        repo.getTalks(conferenceId)
    }

    val selectedTalks: LiveData<List<SessionInfo>> = liveData {
        repo.getTalks(conferenceId)
    }

    fun updateTalkStatus(talk: SessionInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateTalkState(talk)
        }
    }
}