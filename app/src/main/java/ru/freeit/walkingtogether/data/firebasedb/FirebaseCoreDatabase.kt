package ru.freeit.walkingtogether.data.firebasedb

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

interface FirebaseCoreDatabase {

    suspend fun replace(root: String, child: String, value: Map<String, Any>)
    suspend fun remove(root: String, child: String)
    suspend fun element(root: String, child: String) : DataSnapshot

    class Base : FirebaseCoreDatabase {

        companion object {
            private const val databaseUrl = "https://walkingtogether-c014d-default-rtdb.europe-west1.firebasedatabase.app/"
        }

        private val database = FirebaseDatabase.getInstance(databaseUrl)

        override suspend fun replace(root: String, child: String, value: Map<String, Any>) =
            suspendCoroutine<Unit> { continuation ->
                database.getReference(root)
                    .child(child)
                    .setValue(value)
                    .addOnSuccessListener {
                        continuation.resume(Unit)
                    }
                    .addOnFailureListener(continuation::resumeWithException)
            }

        override suspend fun remove(root: String, child: String) = suspendCoroutine<Unit> { continuation ->
            database.getReference(root)
                .child(child)
                .removeValue()
                .addOnSuccessListener {
                    continuation.resume(Unit)
                }
                .addOnFailureListener(continuation::resumeWithException)
        }

        override suspend fun element(root: String, child: String) =
            suspendCoroutine<DataSnapshot> { continuation ->
                database.getReference(root).child(child).get()
                    .addOnSuccessListener { snapshot ->
                        continuation.resume(snapshot)
                    }
                    .addOnFailureListener {
                        continuation.resumeWithException(it)
                    }
            }
    }

}