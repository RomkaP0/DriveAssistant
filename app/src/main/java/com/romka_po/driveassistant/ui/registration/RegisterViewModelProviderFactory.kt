package com.romka_po.driveassistant.ui.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.romka_po.driveassistant.repositories.FBRepository
import com.romka_po.driveassistant.ui.login.LoginViewModel

class RegisterViewModelProviderFactory (
    val repository: FBRepository
//    val firebaseAuth: FirebaseAuth,
//    val netWorkAdapter: NetWorkAdapter
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RegisterViewModel(repository) as T
    }
}