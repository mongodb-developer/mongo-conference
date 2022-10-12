package com.mongodb.mongoize.android.screens.home

import androidx.lifecycle.ViewModel
import com.mongodb.mongoize.RealmRepo

class HomeViewModel : ViewModel() {


    private val repo: RealmRepo = RealmRepo()

    suspend fun getEventsList() {

    }


}