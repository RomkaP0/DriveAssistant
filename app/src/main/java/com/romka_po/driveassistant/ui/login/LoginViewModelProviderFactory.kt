package com.romka_po.driveassistant.ui.login

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.romka_po.driveassistant.interfaces.GoogleOnSignInStartedListener
import com.romka_po.driveassistant.repositories.FBRepository

class LoginViewModelProviderFactory (
    val repository:FBRepository,
//    val firebaseAuth: FirebaseAuth,
//    val netWorkAdapter: NetWorkAdapter
    val application: Application,
    private val listener: GoogleOnSignInStartedListener

): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(repository,application,listener) as T
    }
}