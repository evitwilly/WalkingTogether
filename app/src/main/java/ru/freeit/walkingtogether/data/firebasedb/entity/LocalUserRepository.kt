package ru.freeit.walkingtogether.data.firebasedb.entity

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.freeit.walkingtogether.core.data.AppSharedPreferences

interface LocalUserRepository {
    suspend fun save(user: FirebaseUser)
    fun read() : FirebaseUser
    suspend fun remove() : Boolean

    class Base(private val appPrefs: AppSharedPreferences) : LocalUserRepository {

        private val dispatcher = Dispatchers.Default

        override suspend fun save(user: FirebaseUser) = withContext(dispatcher) {
            if (user.isExists()) {
                user.save(appPrefs)
            }
        }

        override fun read() = FirebaseUser.fromPrefs(appPrefs)

        override suspend fun remove() = withContext(dispatcher) {
            FirebaseUser.empty().save(appPrefs)
        }

    }
}