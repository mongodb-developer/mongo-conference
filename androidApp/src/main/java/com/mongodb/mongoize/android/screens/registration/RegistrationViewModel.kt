package com.mongodb.mongoize.android.screens.registration

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongodb.mongoize.RealmRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistrationViewModel : ViewModel() {

    private val repo = RealmRepo()
    val registrationSuccess = MutableLiveData<Boolean>()

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repo.registration(userName = name, password = password, email = email).run {
                    withContext(Dispatchers.Main) {
                        registrationSuccess.value = true
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    registrationSuccess.value = false
                }
            }
        }
    }

}