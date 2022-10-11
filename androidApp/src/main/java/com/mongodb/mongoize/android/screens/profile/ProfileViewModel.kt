package com.mongodb.mongoize.android.screens.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.mongodb.mongoize.RealmRepo
import com.mongodb.mongoize.UserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

class ProfileViewModel : ViewModel() {

    private val repo = RealmRepo()

    val userInfo: LiveData<UserInfo?> = liveData {
        emitSource(repo.getUserProfile().flowOn(Dispatchers.IO).asLiveData(Dispatchers.Main))
    }

}