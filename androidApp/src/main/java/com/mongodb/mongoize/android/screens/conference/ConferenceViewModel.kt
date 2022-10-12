package com.mongodb.mongoize.android.screens.conference

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongodb.mongoize.RealmRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ConferenceViewModel : ViewModel() {

    private val repo = RealmRepo()

    fun addConference(name: String, location: String, startDate: String, endDate: String) {

        viewModelScope.launch(Dispatchers.IO) {
            repo.addConference(name, location, startDate, endDate)
        }
    }
}