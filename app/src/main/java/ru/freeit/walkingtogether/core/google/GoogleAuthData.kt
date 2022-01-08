package ru.freeit.walkingtogether.core.google

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn

class GoogleAuthData(ctx: Context) {
    private val account = GoogleSignIn.getLastSignedInAccount(ctx)

    fun isLogin() = account != null

    fun id() = account.id ?: ""
    fun displayName() = account.displayName
    fun email() = account.email
}