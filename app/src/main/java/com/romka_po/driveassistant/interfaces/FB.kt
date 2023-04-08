package com.romka_po.driveassistant.interfaces

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser

interface FB {
    suspend fun signUpUser(email: String, password: String):FirebaseUser?

    suspend fun signInUser(email: String, password: String):FirebaseUser?

    suspend fun signInWithGoogle(credential: AuthCredential):FirebaseUser?

    fun saveResource(email: String, map:HashMap<String,Double>): Task<Void>
    fun signOut():FirebaseUser?

    fun getUser():FirebaseUser?

    suspend fun sendForgotPassword(email: String)
}