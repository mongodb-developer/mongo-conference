package com.mongodb.mongoize.android.screens.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel : ViewModel() {

    private val _loginStatus = MutableLiveData<Boolean>()
    val loginStatus: LiveData<Boolean> = _loginStatus

    //private val repo: PassKeeperRepo = PassKeeperRepo()

    fun doLogin(userName: String, password: String) {
        /*viewModelScope.launch(Dispatchers.IO) {
            repo.login(userName, password).apply {
                withContext(Dispatchers.Main) {
                    _loginStatus.value = true
                }
            }
        }*/
    }

}