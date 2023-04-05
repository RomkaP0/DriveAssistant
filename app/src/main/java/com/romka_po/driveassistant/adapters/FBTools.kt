package com.romka_po.driveassistant.adapters

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.romka_po.driveassistant.interfaces.FB
import com.romka_po.driveassistant.model.User
import kotlinx.coroutines.tasks.await

class FBTools : FB {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()


    override suspend fun signUpUser(
        email: String,
        password: String
    ): FirebaseUser? {
        firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        return firebaseAuth.currentUser
    }

    override suspend fun signInUser(email: String, password: String): FirebaseUser? {
        firebaseAuth.signInWithEmailAndPassword(email, password).await()
        return firebaseAuth.currentUser
    }

    override suspend fun signInWithGoogle(credential: AuthCredential): FirebaseUser? {
        firebaseAuth.signInWithCredential(credential)
            .await()
        return firebaseAuth.currentUser
    }


    override fun signOut(): FirebaseUser? {
        TODO("Not yet implemented")
    }

    fun saveUser(email: String, name: String) = firestore.collection("users").document(email).set(
        User(email, name)
    )

    override fun getUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    override suspend fun sendForgotPassword(email: String) {
        firebaseAuth.sendPasswordResetEmail(email).await()
    }


}