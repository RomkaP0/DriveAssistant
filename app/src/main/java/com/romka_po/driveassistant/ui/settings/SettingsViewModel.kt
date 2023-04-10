package com.romka_po.driveassistant.ui.settings

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.romka_po.driveassistant.repositories.FBRepository
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val repository: FBRepository,
) : ViewModel() {
    private val fbUser = MutableLiveData<FirebaseUser?>()
    val currentUser get() = fbUser


    fun signOut() {
        try {

            val user = repository.signOut()
            fbUser.postValue(user)
//                eventsChannel.send(AllEvents.Message("login success"))

        } catch (e: Exception) {
            val error = e.toString().split(":").toTypedArray()
            Log.d("SignIn", "signInUser: ${error[1]}")
//            eventsChannel.send(AllEvents.Error(error[1]))
        }
    }
    fun getUser() = viewModelScope.launch {
        val user = repository.getUser()
        fbUser.postValue(user)
    }

}