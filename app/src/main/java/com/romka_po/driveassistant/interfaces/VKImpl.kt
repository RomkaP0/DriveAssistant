package com.romka_po.driveassistant.interfaces

import androidx.activity.ComponentActivity

interface VKImpl {
    fun registerVKAuthProvider(activity: ComponentActivity)
    fun launchVKAuthProvider()
    fun getVKEmail(): String


    }