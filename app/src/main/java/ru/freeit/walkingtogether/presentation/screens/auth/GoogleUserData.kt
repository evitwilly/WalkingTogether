package ru.freeit.walkingtogether.presentation.screens.auth

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn

class GoogleUserData(ctx: Context) {
    private val account = GoogleSignIn.getLastSignedInAccount(ctx)

    fun isLogin() : Boolean {
        return account != null
    }

    fun id() = account.id ?: ""
    fun displayName() = account.displayName
    fun email() = account.email
}