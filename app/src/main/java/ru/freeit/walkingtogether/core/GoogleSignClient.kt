package ru.freeit.walkingtogether.core

import android.app.Activity
import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class GoogleSignClient(ctx: Context) {
    private val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .build()

    private val client = GoogleSignIn.getClient(ctx, options)

    fun signOut() = client.signOut()
    fun signInIntent() = client.signInIntent
}