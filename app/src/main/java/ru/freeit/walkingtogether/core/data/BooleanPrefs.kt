package ru.freeit.walkingtogether.core.data

interface BooleanPrefs {
    fun saveBoolean(key: String, value: Boolean)
    fun bool(key: String, def: Boolean) : Boolean
}