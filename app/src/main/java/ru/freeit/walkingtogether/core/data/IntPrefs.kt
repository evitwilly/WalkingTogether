package ru.freeit.walkingtogether.core.data

interface IntPrefs {
    fun saveInt(key: String, value: Int)
    fun int(key: String, def: Int) : Int
}