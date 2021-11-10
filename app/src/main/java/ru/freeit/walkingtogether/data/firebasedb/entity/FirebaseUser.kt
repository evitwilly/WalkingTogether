package ru.freeit.walkingtogether.data.firebasedb.entity

import com.google.firebase.database.DataSnapshot
import ru.freeit.walkingtogether.core.AppSharedPreferences
import ru.freeit.walkingtogether.domain.entity.User
import ru.freeit.walkingtogether.presentation.screens.register.AvatarImages

data class FirebaseUser(
    private val id: String,
    private val name: String = "",
    private val bio: String = "",
    private val isFemale: Boolean = true,
    private val avatarId: Int = -1
) {

    fun isExists() = id.isNotBlank()
    fun id() = id

    fun encode() = mapOf(
        nameKey to name,
        bioKey to bio,
        isFemaleKey to isFemale,
        avatarIdKey to avatarId
    )

    fun toDomain(images: AvatarImages) = User(id, name, bio, isFemale, images.drawableBy(avatarId))

    suspend fun save(appPrefs: AppSharedPreferences) {
        appPrefs.saveBoolean(isFemaleKey, isFemale)
        appPrefs.saveString(nameKey, name)
        appPrefs.saveString(bioKey, bio)
        appPrefs.saveString(idKey, id)
        appPrefs.saveInt(avatarIdKey, avatarId)
        appPrefs.commit()
    }

    companion object {
        private const val idKey = "id"
        private const val nameKey = "name"
        private const val bioKey = "bio"
        private const val isFemaleKey = "isFemale"
        private const val avatarIdKey = "avatarId"

        fun empty() = FirebaseUser("")

        fun restore(appPrefs: AppSharedPreferences) = FirebaseUser(
            appPrefs.str(idKey, ""),
            appPrefs.str(nameKey, ""),
            appPrefs.str(bioKey, ""),
            appPrefs.bool(isFemaleKey, true),
            appPrefs.int(avatarIdKey, -1)
        )

        fun fromSnapshot(id: String, data: DataSnapshot) : FirebaseUser {
            return FirebaseUser(
                id,
                data.child(nameKey).value.toString(),
                data.child(bioKey).value.toString(),
                data.child(isFemaleKey).value as Boolean,
                data.child(avatarIdKey).value as Int
            )
        }
    }
}