package com.romka_po.driveassistant.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.romka_po.driveassistant.repositories.FBRepository

class SettingViewModelProviderFactory (
    val repository:FBRepository,
//    val firebaseAuth: FirebaseAuth,
//    val netWorkAdapter: NetWorkAdapter

): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SettingsViewModel(repository) as T
    }
}