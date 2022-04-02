package com.zoe.publisher

import android.content.Context
import android.content.SharedPreferences

/** Singleton Object*/
object UserManager {
    private const val APP_NAME = "Publisher"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    fun init(context: Context) {
        preferences = context.getSharedPreferences(APP_NAME, MODE)
    }
    /**
     * SharedPreferences extension function, so we won't need to call edit()
    and apply()
     * ourselves on every SharedPreferences operation.
     */
    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }


    const val USER_TOKEN = "user_token"
    const val USER_NAME = "user_name"


    var userToken: String?
        get() = preferences.getString(USER_TOKEN, null)
        set(token) = preferences.edit {
            it.putString(USER_TOKEN, token)
        }

    var userName: String?
        get() = preferences.getString(USER_NAME, null)
        set(name) = preferences.edit {
            it.putString(USER_NAME, name)
        }

}
