package ru.freeit.walkingtogether.presentation.base.google

import android.app.Activity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class GoogleAccountLogin(fragment: Fragment) {

    private var onSuccess: ((id: String) -> Unit)? = null
    private var onError: (() -> Unit)? = null
    private var onCancel: (() -> Unit)? = null

    private val googleSignInLauncher = fragment.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
        if (activityResult.resultCode == Activity.RESULT_CANCELED) {
            onCancel?.invoke()
            return@registerForActivityResult
        }
        val task: Task<GoogleSignInAccount> =
            GoogleSignIn.getSignedInAccountFromIntent(activityResult.data)
        try {
            val account = task.getResult(ApiException::class.java)
            onSuccess?.invoke(account.id!!)
        } catch (e: ApiException) {
            onError?.invoke()
        }
    }

    fun onSuccess(success: (id: String) -> Unit) {
        this.onSuccess = success
    }

    fun onError(error: () -> Unit) {
        this.onError = error
    }

    fun onCancel(cancel: () -> Unit) {
        this.onCancel = cancel
    }

    fun login(sign: SignInIntent) {
        googleSignInLauncher.launch(sign.intent())
    }

}