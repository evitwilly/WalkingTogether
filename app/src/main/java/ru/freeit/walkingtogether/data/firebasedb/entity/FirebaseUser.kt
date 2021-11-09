package ru.freeit.walkingtogether.presentation.screens.auth

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.google.firebase.database.DataSnapshot

// TODO maybe add a new User class
//data class User(
//    private val id: String,
//    private val name: String,
//    private val bio: String,
//    private val isFemale: Boolean = true,
//    private val drawableId: Int = -1
//) {
//
//}

class AppPreferences(ctx: Context) {

    private val appPrefsKey = "app_prefs_key"

    private val appPrefs = ctx.getSharedPreferences(appPrefsKey, MODE_PRIVATE)


}

data class FirebaseUser(
    private val id: String,
    private val name: String = "",
    private val bio: String = "",
    private val isFemale: Boolean = true,
    private val drawableId: Int = -1
) {

    fun isExists() = id == ""
    fun id() = id

    fun encode() = mapOf(
        nameKey to name,
        bioKey to bio,
        isFemaleKey to isFemale,
        drawableIdKey to drawableId
    )



    companion object {
        private const val nameKey = "name"
        private const val bioKey = "bio"
        private const val isFemaleKey = "isFemale"
        private const val drawableIdKey = "avatarId"

        fun empty() = FirebaseUser("")

        fun fromSnapshot(id: String, data: DataSnapshot) : FirebaseUser {
            return FirebaseUser(
                id,
                data.child(nameKey).value.toString(),
                data.child(bioKey).value.toString(),
                data.child(isFemaleKey).value as Boolean,
                data.child(drawableIdKey).value as Int
            )
        }
    }
}