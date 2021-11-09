package ru.freeit.walkingtogether.core

class FirebaseException(private val msg: String, private val details: String) : Exception(msg)