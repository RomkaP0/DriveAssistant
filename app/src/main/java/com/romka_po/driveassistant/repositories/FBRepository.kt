package com.romka_po.driveassistant.repositories

import androidx.activity.ComponentActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import com.romka_po.driveassistant.adapters.FBTools
import com.romka_po.driveassistant.adapters.VKTools

class FBRepository(val fbTools: FBTools, val vkTools: VKTools) {

    suspend fun signUpUser(email: String, password: String): FirebaseUser? {
        return fbTools.signUpUser(email, password)
    }

    suspend fun signInUser(email: String, password: String): FirebaseUser? {
        return fbTools.signInUser(email, password)
    }

    suspend fun signInWithGoogle(credential: AuthCredential) : FirebaseUser? {
        return fbTools.signInWithGoogle(credential)
    }

//    fun signInWithVK(activity: Activity):UserId{
//        return fbTools.signInWithVK(activity)
//    }

    fun getUser(): FirebaseUser? {
        return fbTools.getUser()
    }

    suspend fun sendForgotPassword(email: String): Boolean {
        fbTools.sendForgotPassword(email)
        return true
    }

    fun registerVKAuthProvider(activity: ComponentActivity){
        return vkTools.registerVKAuthProvider(activity)
    }
    fun launchVKAuthProvider(){
        return vkTools.launchVKAuthProvider()
    }
    fun getVKEmail(): String{
        return vkTools.getVKEmail()
    }
    fun saveResource(email: String, map:HashMap<String,Double>): Task<Void> {
        return fbTools.saveResource(email,map)
    }
}
