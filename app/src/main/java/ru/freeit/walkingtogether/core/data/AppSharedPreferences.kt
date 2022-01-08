package ru.freeit.walkingtogether.core.data

import android.content.Context

interface AppSharedPreferences : BooleanPrefs, IntPrefs, StringPrefs {

    fun apply()
    suspend fun commit() : Boolean

    class Base(ctx: Context) : AppSharedPreferences {

        private val appPrefsKey = "app_prefs_key"

        private val appPrefs = ctx.getSharedPreferences(appPrefsKey, Context.MODE_PRIVATE)

        private val edit = appPrefs.edit()

        override fun saveInt(key: String, value: Int) { edit.putInt(key, value) }
        override fun saveString(key: String, value: String) { edit.putString(key, value) }
        override fun saveBoolean(key: String, value: Boolean) { edit.putBoolean(key, value) }

        override fun bool(key: String, def: Boolean) = appPrefs.getBoolean(key, def)
        override fun int(key: String, def: Int) = appPrefs.getInt(key, def)
        override fun str(key: String, def: String) = appPrefs.getString(key, def) ?: def

        override fun apply() = edit.apply()
        override suspend fun commit() = edit.commit()
    }


}