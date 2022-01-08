package ru.freeit.walkingtogether.core.data

interface StringPrefs {
    fun saveString(key: String, value: String)
    fun str(key: String, def: String) : String
}