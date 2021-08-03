package com.heartsun.pithuwakhanipani.data

import android.content.SharedPreferences
import androidx.core.content.edit

class Prefs(private val sharedPreferences: SharedPreferences) {

    companion object {
        const val PREF_FIRST_TIME = "prefs.FIRST_TIME"
        const val PREF_UNIQUE_ID = "prefs.UNIQUE_ID"
        const val PREF_LOGGED_IN = "prefs.LOGGED_IN"
        const val PREF_TOKEN = "prefs.TOKEN"

        const val UserDetails = "prefs.UserDetails"
        const val UserID = "prefs.UserID"

        const val PHONE_NUMBER = "prefs.phone_number"
        const val EMAIL = "prefs.email"

        const val TIME_RESEND = "prefs.time_resend"
        const val VERIFY_WAITING = "prefs.verify_waiting"
    }


    var isFirstTime: Boolean
        get() = sharedPreferences.getBoolean(PREF_FIRST_TIME, true)
        set(value) {
            sharedPreferences.edit { putBoolean(PREF_FIRST_TIME, value) }
        }


    var isLoggedIn: Boolean
        get() = sharedPreferences.getBoolean(PREF_LOGGED_IN, false)
        set(value) {
            sharedPreferences.edit { putBoolean(PREF_LOGGED_IN, value) }
        }


    var isVerifyWaiting: Boolean
        get() = sharedPreferences.getBoolean(VERIFY_WAITING, false)
        set(value) {
            sharedPreferences.edit { putBoolean(VERIFY_WAITING, value) }
        }

    var authToken: String?
        get() {
            val token = sharedPreferences.getString(PREF_TOKEN, null)
            return "Token ${token ?: return null}"
        }
        set(value) {
            sharedPreferences.edit { putString(PREF_TOKEN, value) }
        }


    var userId: Int?
        get() {
            return sharedPreferences.getInt(PREF_UNIQUE_ID, 0)
        }
        set(value) {
            sharedPreferences.edit {
                if (value != null) {
                    putInt(PREF_UNIQUE_ID, value)
                }
            }
        }

    var timeResend: String?
        get() {
            return sharedPreferences.getString(TIME_RESEND, "0")
        }
        set(value) {
            sharedPreferences.edit { putString(TIME_RESEND, value) }
        }


    var phoneNumber: String?
        get() {
            val phoneNumber = sharedPreferences.getString(PHONE_NUMBER, null)
            return phoneNumber ?: return null
        }
        set(value) {
            sharedPreferences.edit { putString(PHONE_NUMBER, value) }
        }

    var email: String?
        get() {
            val email = sharedPreferences.getString(EMAIL, null)
            return email ?: return null
        }
        set(value) {
            sharedPreferences.edit { putString(EMAIL, value) }
        }




    fun logout() {
        sharedPreferences.edit(true) {
            clear()
        }
        isFirstTime = false
    }
}