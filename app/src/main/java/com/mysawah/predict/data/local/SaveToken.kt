package com.mysawah.predict.data.local

import android.content.Context
import android.content.SharedPreferences

object SaveToken {
    private const val PREF_NAME = "mysawah_pref"
    private const val KEY_TOKEN = "auth_token"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    // Simpan Token
    fun saveToken(context: Context, token: String) {
        val editor = getPreferences(context).edit()
        editor.putString(KEY_TOKEN, token)
        editor.apply()
    }

    // Ambil Token
    fun getToken(context: Context): String? {
        return getPreferences(context).getString(KEY_TOKEN, null)
    }

    // Hapus Token (untuk Logout)
    fun clearToken(context: Context) {
        val editor = getPreferences(context).edit()
        editor.remove(KEY_TOKEN)
        editor.apply()
    }
}