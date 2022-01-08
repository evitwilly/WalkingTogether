package ru.freeit.walkingtogether.core.exception

class FirebaseException(private val msg: String, private val details: String) : Exception(msg)