package com.romka_po.driveassistant.ui.registration

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.romka_po.driveassistant.repositories.FBRepository

class RegisterViewModel(
    private val repository: FBRepository,
//    private val networkControl: NetWorkAdapter,
//    private val firebaseAuth: FirebaseAuth
) : ViewModel() {
    private val fbUser = MutableLiveData<FirebaseUser?>()
    val currentUser get() = fbUser
}