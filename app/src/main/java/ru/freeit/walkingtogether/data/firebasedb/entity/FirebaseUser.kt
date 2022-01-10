package ru.freeit.walkingtogether.data.firebasedb.entity

import com.google.firebase.database.DataSnapshot
import ru.freeit.walkingtogether.core.data.AppSharedPreferences
import ru.freeit.walkingtogether.domain.entity.UserDomain
import ru.freeit.walkingtogether.presentation.base.AvatarImages

data class FirebaseUser(
    private val id: String,
    private val name: String = "",
    private val bio: String = "",
    private val isFemale: Boolean = true,
    private val avatarId: Int = -1
) {

    fun isExists() = id.isNotBlank()
    fun id() = id

    fun toSnapshot() = mapOf(nameKey to name, bioKey to bio, isFemaleKey to isFemale, avatarIdKey to avatarId)
    fun toDomain(images: AvatarImages) = UserDomain(id, name, bio, isFemale, images.drawableBy(avatarId))

    fun name() = name
    fun desc() = bio

    suspend fun save(appPrefs: AppSharedPreferences) = appPrefs.run {
        saveBoolean(isFemaleKey, isFemale)
        saveString(nameKey, name)
        saveString(bioKey, bio)
        saveString(idKey, id)
        saveInt(avatarIdKey, avatarId)
        commit()
    }

    companion object {
        private const val idKey = "id"
        private const val nameKey = "name"
        private const val bioKey = "bio"
        private const val isFemaleKey = "isFemale"
        private const val avatarIdKey = "avatarId"

        fun empty() = FirebaseUser("")

        fun fromPrefs(appPrefs: AppSharedPreferences) = appPrefs.run { FirebaseUser(
            str(idKey, ""),
            str(nameKey, ""),
            str(bioKey, ""),
            bool(isFemaleKey, true),
            int(avatarIdKey, -1)
        ) }

        fun fromSnapshot(id: String, data: DataSnapshot) : FirebaseUser {
            return FirebaseUser(
                id,
                data.child(nameKey).value.toString(),
                data.child(bioKey).value.toString(),
                data.child(isFemaleKey).value as Boolean,
                (data.child(avatarIdKey).value as Long).toInt()
            )
        }

    }
}