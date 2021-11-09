package ru.freeit.walkingtogether.presentation.screens.auth

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class MyFirebaseDatabase {

    private val databaseUrl = "https://walkingtogether-c014d-default-rtdb.europe-west1.firebasedatabase.app/"
    private val database = FirebaseDatabase.getInstance(databaseUrl)

    private val users = "users"

    suspend fun add(user: FirebaseUser) = suspendCoroutine<Unit> { continuation ->
        database.getReference(users)
            .child(user.id())
            .setValue(user.encode())
            .addOnSuccessListener {
                continuation.resume(Unit)
            }
            .addOnFailureListener(continuation::resumeWithException)
    }

    suspend fun user(id: String) : FirebaseUser = suspendCoroutine { continuation ->
        database.getReference(users).child(id).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = if (snapshot.exists()) {
                    FirebaseUser.fromSnapshot(id, snapshot)
                } else {
                    FirebaseUser.empty()
                }
                continuation.resume(user)
            }

            override fun onCancelled(error: DatabaseError) {
                continuation.resumeWithException(FirebaseException(error.message, error.details))
            }
        })
    }

}