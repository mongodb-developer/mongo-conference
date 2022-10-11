package com.mongodb.mongoize.android.screens.login

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.lifecycle.*
import com.mongodb.mongoize.RealmRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel : ViewModel() {

    private val repo: RealmRepo = RealmRepo()

    private val _loginStatus = MutableLiveData<Boolean>()
    val loginStatus: LiveData<Boolean> = _loginStatus

    val alreadyLoggedIn: LiveData<Boolean> = repo.isUserLoggedIn().asLiveData(Dispatchers.Main)

    fun doLogin(userName: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.login(userName, password).apply {
                withContext(Dispatchers.Main) {
                    _loginStatus.value = true
                }
            }
        }
    }

}