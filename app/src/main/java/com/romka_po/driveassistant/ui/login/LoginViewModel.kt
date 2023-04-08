package com.romka_po.driveassistant.ui.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.romka_po.driveassistant.R
import com.romka_po.driveassistant.interfaces.GoogleOnSignInStartedListener
import com.romka_po.driveassistant.model.MapOfResource
import com.romka_po.driveassistant.repositories.FBRepository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: FBRepository,
//    private val networkControl: NetWorkAdapter,
//    private val firebaseAuth: FirebaseAuth
    private val application: Application, private val listener: GoogleOnSignInStartedListener
) :
    AndroidViewModel(application = application) {
    private val fbUser = MutableLiveData<FirebaseUser?>()
    private val vkEmail = MutableLiveData<String?>()
    val currentUser get() = fbUser
    val vkAuthEmail get() = vkEmail


    fun signInUser(email: String, password: String) = viewModelScope.launch {
        when {
            email.isEmpty() -> {
//                eventsChannel.send(AllEvents.ErrorCode(1))
            }

            password.isEmpty() -> {
//                eventsChannel.send(AllEvents.ErrorCode(2))
            }

            else -> {
                actualSignInUser(email, password)
            }
        }
    }

    private fun actualSignInUser(email: String, password: String) = viewModelScope.launch {
        try {
            val user = repository.signInUser(email, password)
            user?.let {
                fbUser.postValue(it)
                repository.saveResource(it.email!!, MapOfResource.mapOfResource)

//                eventsChannel.send(AllEvents.Message("login success"))
            }
        } catch (e: Exception) {

            val error = e.toString().split(":").toTypedArray()
            Log.d("SignIn", "signInUser: ${error[1]}")
            Log.d("typeSignIn", "TypeException: ${e::class}")
//            eventsChannel.send(AllEvents.Error(error[1]))
        }
    }


    fun signInWithGoogle(idToken: String) = viewModelScope.launch {
        try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val user = repository.signInWithGoogle(credential)
            user?.let {
                fbUser.postValue(it)
                repository.saveResource(it.email!!, MapOfResource.mapOfResource)

//                eventsChannel.send(AllEvents.Message("login success"))
            }
        } catch (e: Exception) {
            val error = e.toString().split(":").toTypedArray()
            Log.d("SignIn", "signInUser: ${error[1]}")
//            eventsChannel.send(AllEvents.Error(error[1]))
        }
    }
    fun launchVKAuthProvider(){
        repository.launchVKAuthProvider()
    }

    fun getVKEmail() {
        try {
            val email = repository.getVKEmail()
            email?.let {
                vkEmail.postValue(it)
//                eventsChannel.send(AllEvents.Message("login success"))
            }
        } catch (e: Exception) {
            val error = e.toString().split(":").toTypedArray()
            Log.d("SignIn", "signInUser: ${error[1]}")
//            eventsChannel.send(AllEvents.Error(error[1]))
        }
    }

    suspend fun syncVKtoFB() = MainScope().launch{
        if (vkEmail.isInitialized && vkEmail.value !="null") {
            val email = "vk_${vkEmail.value.toString()}"
            val password = vkEmail.value.toString()
            try {
                val user = repository.signInUser(email, password)
                user?.let {
                    fbUser.postValue(it)
                    repository.saveResource(it.email!!, MapOfResource.mapOfResource)

//                eventsChannel.send(AllEvents.Message("login success"))
                }
            } catch (e: FirebaseAuthInvalidUserException) {
                try {
                    val user = repository.signUpUser(email, password)
                    user?.let {
                        fbUser.postValue(it)
                        repository.saveResource(it.email!!, MapOfResource.mapOfResource)
//                eventsChannel.send(AllEvents.Message("sign up success"))
                    }
                } catch (e: Exception) {

                    val error = e.toString().split(":").toTypedArray()
                    Log.d("SignIn", "signInUser: ${error[1]}")
                    Log.d("typeSignIn", "TypeException: ${e::class}")
//            eventsChannel.send(AllEvents.Error(error[1]))
                }
            }
        }
    }


    fun verifySendPasswordReset(email: String) {
        if (email.isEmpty()) {
            viewModelScope.launch {
//                eventsChannel.send(AllEvents.ErrorCode(1))
            }
        } else {
            sendPasswordResetEmail(email)
        }

    }

    private fun sendPasswordResetEmail(email: String) = viewModelScope.launch {
        try {
            val result = repository.sendForgotPassword(email)
            if (result) {
//                eventsChannel.send(AllEvents.Message("reset email sent"))
            } else {
//                eventsChannel.send(AllEvents.Error("could not send password reset"))
            }
        } catch (e: Exception) {
            val error = e.toString().split(":").toTypedArray()
//            Log.d(TAG, "signInUser: ${error[1]}")
//            eventsChannel.send(AllEvents.Error(error[1]))
        }
    }

    fun getUser() = viewModelScope.launch {
        val user = repository.getUser()
        fbUser.postValue(user)
    }


    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(application.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

    private val googleSignInClient = GoogleSignIn.getClient(application, gso)

    fun signIn() {
        listener.onSignInStarted(googleSignInClient)
    }

}

