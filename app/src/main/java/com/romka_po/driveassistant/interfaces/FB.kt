package com.romka_po.driveassistant.interfaces

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser

interface FB {
    suspend fun signUpUser(email: String, password: String):FirebaseUser?

    suspend fun signInUser(email: String, password: String):FirebaseUser?

    suspend fun signInWithGoogle(acct: GoogleSignInAccount):FirebaseUser?

    fun signOut():FirebaseUser?

    fun getUser():FirebaseUser?

    suspend fun sendForgotPassword(email: String)
}