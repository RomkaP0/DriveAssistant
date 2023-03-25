package com.romka_po.driveassistant.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.romka_po.driveassistant.repositories.FBRepository

class SplashViewModelProviderFactory  (
    val repository: FBRepository
//    val firebaseAuth: FirebaseAuth,
//    val netWorkAdapter: NetWorkAdapter
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SplashViewModel(repository) as T
    }
}