package ru.freeit.walkingtogether.core

import android.content.Context

interface BooleanPrefs {
    fun saveBoolean(key: String, value: Boolean)
    fun bool(key: String, def: Boolean) : Boolean
}

interface IntPrefs {
    fun saveInt(key: String, value: Int)
    fun int(key: String, def: Int) : Int
}

interface StringPrefs {
    fun saveString(key: String, value: String)
    fun str(key: String, def: String) : String
}

class AppSharedPreferences(ctx: Context) : BooleanPrefs, IntPrefs, StringPrefs {

    private val appPrefsKey = "app_prefs_key"

    private val appPrefs = ctx.getSharedPreferences(appPrefsKey, Context.MODE_PRIVATE)

    override fun saveBoolean(key: String, value: Boolean) {
        appPrefs.edit()
            .putBoolean(key, value)
            .apply()
    }

    override fun bool(key: String, def: Boolean) = appPrefs.getBoolean(key, def)

    override fun saveInt(key: String, value: Int) {
        appPrefs.edit()
            .putInt(key, value)
            .apply()
    }

    override fun int(key: String, def: Int) = appPrefs.getInt(key, def)

    override fun saveString(key: String, value: String) {
        appPrefs.edit()
            .putString(key, value)
            .apply()
    }

    override fun str(key: String, def: String) = appPrefs.getString(key, def) ?: def


}