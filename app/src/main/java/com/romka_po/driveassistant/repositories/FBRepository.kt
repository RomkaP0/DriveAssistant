package com.romka_po.driveassistant.repositories

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import com.romka_po.driveassistant.adapters.FBTools

class FBRepository(val fbTools: FBTools) {

    suspend fun signUpUser(email: String, password: String): FirebaseUser? {
        return fbTools.signUpUser(email, password)
    }

    suspend fun signInUser(email: String, password: String): FirebaseUser? {
        return fbTools.signInUser(email, password)
    }

    suspend fun signInWithGoogle(acct: GoogleSignInAccount): FirebaseUser? {
        return fbTools.signInWithGoogle(acct)
    }


    fun getUser(): FirebaseUser? {
        return fbTools.getUser()
    }

    suspend fun sendForgotPassword(email: String): Boolean {
        fbTools.sendForgotPassword(email)
        return true
    }
}
