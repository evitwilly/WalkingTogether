package ru.freeit.walkingtogether.presentation.screens.auth

import android.app.Activity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class GoogleSignClient(activity: Activity) {
    private val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .build()

    private val client = GoogleSignIn.getClient(activity, options)

    fun signOut() = client.signOut()
    fun signInIntent() = client.signInIntent
}