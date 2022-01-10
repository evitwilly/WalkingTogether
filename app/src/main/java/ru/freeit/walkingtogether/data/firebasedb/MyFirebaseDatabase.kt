package ru.freeit.walkingtogether.data.firebasedb

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.freeit.walkingtogether.core.exception.FirebaseException
import ru.freeit.walkingtogether.data.firebasedb.entity.FirebaseUser

import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

interface UserFirebaseDatabase {

    companion object {
        private const val users = "users"
    }

    suspend fun add(user: FirebaseUser)
    suspend fun remove(user: FirebaseUser)
    suspend fun user(id: String) : FirebaseUser

    class Base(private val coreDatabase: FirebaseCoreDatabase) : UserFirebaseDatabase {

        private val dispatcher = Dispatchers.Default

        override suspend fun add(user: FirebaseUser) = withContext(dispatcher) {
            coreDatabase.replace(users, user.id(), user.toSnapshot())
        }

        override suspend fun remove(user: FirebaseUser) = withContext(dispatcher) {
            coreDatabase.remove(users, user.id())
        }

        override suspend fun user(id: String) = withContext(dispatcher) {
            val snapshot = coreDatabase.element(users, id)
            if (snapshot.exists()) {
                FirebaseUser.fromSnapshot(id, snapshot)
            } else {
                FirebaseUser.empty()
            }
        }

    }


}
