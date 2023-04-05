package com.romka_po.driveassistant.interfaces

import com.google.android.gms.auth.api.signin.GoogleSignInClient

interface GoogleOnSignInStartedListener {
    fun onSignInStarted(client: GoogleSignInClient?)
}