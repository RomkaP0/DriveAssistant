package com.romka_po.driveassistant.ui.registration

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.romka_po.driveassistant.repositories.FBRepository
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val repository: FBRepository,
//    private val networkControl: NetWorkAdapter,
//    private val firebaseAuth: FirebaseAuth
) : ViewModel() {
    private val fbUser = MutableLiveData<FirebaseUser?>()
    val currentUser get() = fbUser

    fun signUpUser(email : String , password: String , confirmPass : String)= viewModelScope.launch {
        when{
            email.isEmpty() -> {
//                eventsChannel.send(AllEvents.ErrorCode(1))
            }
            password.isEmpty() -> {
//                eventsChannel.send(AllEvents.ErrorCode(2))
            }
            password != confirmPass ->{
//                eventsChannel.send(AllEvents.ErrorCode(3))
            }
            else -> {
                actualSignUpUser(email, password)
            }
        }
    }

    private fun actualSignUpUser(email:String , password: String) = viewModelScope.launch {
        try {
            val user = repository.signUpUser(email, password)
            user?.let {
                fbUser.postValue(it)
//                eventsChannel.send(AllEvents.Message("sign up success"))
            }
        }catch(e:Exception){
            val error = e.toString().split(":").toTypedArray()
//            Log.d(TAG, "signInUser: ${error[1]}")
//            eventsChannel.send(AllEvents.Error(error[1]))
        }
    }
}