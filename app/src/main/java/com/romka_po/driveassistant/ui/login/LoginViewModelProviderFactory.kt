package com.romka_po.driveassistant.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.romka_po.driveassistant.repositories.FBRepository

class LoginViewModelProviderFactory (
    val repository:FBRepository
//    val firebaseAuth: FirebaseAuth,
//    val netWorkAdapter: NetWorkAdapter
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(repository) as T
    }
}