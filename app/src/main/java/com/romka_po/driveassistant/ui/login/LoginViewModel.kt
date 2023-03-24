package com.romka_po.driveassistant.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.romka_po.driveassistant.repositories.FBRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: FBRepository,
//    private val networkControl: NetWorkAdapter,
//    private val firebaseAuth: FirebaseAuth
) :
    ViewModel() {
    private val fbUser = MutableLiveData<FirebaseUser?>()
    val currentUser get() = fbUser


    fun signInUser(email: String , password: String) = viewModelScope.launch{
        when {
            email.isEmpty() -> {
//                eventsChannel.send(AllEvents.ErrorCode(1))
            }
            password.isEmpty() -> {
//                eventsChannel.send(AllEvents.ErrorCode(2))
            }
            else -> {
                actualSignInUser(email , password)
            }
        }
    }

    private fun actualSignInUser(email:String, password: String) = viewModelScope.launch {
        try {
            val user = repository.signInUser(email, password)
            user?.let {
                fbUser.postValue(it)
//                eventsChannel.send(AllEvents.Message("login success"))
            }
        }catch(e:Exception){
            val error = e.toString().split(":").toTypedArray()
            Log.d("SignIn", "signInUser: ${error[1]}")
//            eventsChannel.send(AllEvents.Error(error[1]))
        }
    }


    fun verifySendPasswordReset(email: String){
        if(email.isEmpty()){
            viewModelScope.launch {
//                eventsChannel.send(AllEvents.ErrorCode(1))
            }
        }else{
            sendPasswordResetEmail(email)
        }

    }
    private fun sendPasswordResetEmail(email: String) = viewModelScope.launch {
        try {
            val result = repository.sendForgotPassword(email)
            if (result){
//                eventsChannel.send(AllEvents.Message("reset email sent"))
            }else{
//                eventsChannel.send(AllEvents.Error("could not send password reset"))
            }
        }catch (e : Exception){
            val error = e.toString().split(":").toTypedArray()
//            Log.d(TAG, "signInUser: ${error[1]}")
//            eventsChannel.send(AllEvents.Error(error[1]))
        }
    }

}