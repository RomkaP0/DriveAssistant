package com.romka_po.driveassistant.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.romka_po.driveassistant.repositories.AutoAPIRepository
import com.romka_po.driveassistant.ui.home.HomeViewModel

class ViewModelProviderFactory(
    val autoAPIRepository: AutoAPIRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(autoAPIRepository) as T
    }
}