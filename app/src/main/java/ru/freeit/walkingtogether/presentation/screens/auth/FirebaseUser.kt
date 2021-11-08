package ru.freeit.walkingtogether.presentation.screens.auth

import com.google.firebase.database.DataSnapshot

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
        private const val nameKey = "nameKey"
        private const val bioKey = "bioKey"
        private const val isFemaleKey = "isFemaleKey"
        private const val drawableIdKey = "isDrawableIdKey"

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