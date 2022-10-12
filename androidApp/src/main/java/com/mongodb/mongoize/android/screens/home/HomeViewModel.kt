package com.mongodb.mongoize.android.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.mongodb.mongoize.ConferenceInfo
import com.mongodb.mongoize.RealmRepo

class HomeViewModel : ViewModel() {

    private val repo: RealmRepo = RealmRepo()

    val events: LiveData<List<ConferenceInfo>> = liveData {
        emitSource(repo.getEventLists().asLiveData())
    }

}