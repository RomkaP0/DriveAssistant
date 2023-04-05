package com.romka_po.driveassistant.adapters

import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import com.romka_po.driveassistant.adapters.VKTools.AuthLauncher.authLauncher
import com.romka_po.driveassistant.adapters.VKTools.AuthLauncher.userEmail
import com.romka_po.driveassistant.interfaces.VKImpl
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope


class VKTools:VKImpl {
    object AuthLauncher {
        lateinit var authLauncher: ActivityResultLauncher<Collection<VKScope>>
        lateinit var userEmail:String

    }
    override fun registerVKAuthProvider(activity: ComponentActivity){
        authLauncher = VK.login(activity) { result : VKAuthenticationResult ->
            when (result) {
                is VKAuthenticationResult.Success -> {
                    userEmail = result.token.email.toString()
                }
                is VKAuthenticationResult.Failed -> {

                }
            }
        }
    }

    override fun launchVKAuthProvider(){
        authLauncher.launch(arrayListOf(VKScope.WALL, VKScope.PHOTOS, VKScope.EMAIL))
    }
    override fun getVKEmail(): String {
        return userEmail
    }
}