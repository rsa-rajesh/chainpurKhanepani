package com.heartsun.pithuwakhanipani.data

import android.content.SharedPreferences
import androidx.core.content.edit

class Prefs(private val sharedPreferences: SharedPreferences) {

    companion object {
        const val PREF_FIRST_TIME = "prefs.FIRST_TIME"
        const val PREF_UNIQUE_ID = "prefs.UNIQUE_ID"
        const val PREF_LOGGED_IN = "prefs.LOGGED_IN"
        const val PREF_TOKEN = "prefs.TOKEN"
        const val UserID = "prefs.UserID"
        const val PHONE_NUMBER = "prefs.phone_number"
        const val EMAIL = "prefs.email"
        const val IMAGE_DB = "prefs.image_db"
        const val RECORD_DB = "prefs.record_db"
        const val LAST_SEARCHED_MEMBER_ID = "prefs.last_searched_member_id"
        const val NO_OF_TAPS = "prefs.number_of_taps"

    }



    var memberIds: String?
        get() = sharedPreferences.getString(LAST_SEARCHED_MEMBER_ID, "")
        set(value) {
            sharedPreferences.edit { putString(LAST_SEARCHED_MEMBER_ID, value) }
        }

    var imageDb: String?
        get() = sharedPreferences.getString(IMAGE_DB, "")
        set(value) {
            sharedPreferences.edit { putString(IMAGE_DB, value) }
        }

    var recordDb: String?
        get() = sharedPreferences.getString(RECORD_DB, "")
        set(value) {
            sharedPreferences.edit { putString(RECORD_DB, value) }
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

    var noOfTaps: String?
        get() {
            val noOfTaps = sharedPreferences.getString(NO_OF_TAPS, null)
            return noOfTaps ?: return null
        }
        set(value) {
            sharedPreferences.edit { putString(NO_OF_TAPS, value) }
        }



//    var profileDetail: UserDetailsResponse?
//        get() {
//            val jsonString = sharedPreferences.getString(UserDetails, null)
//            return Gson().fromJson(
//                jsonString ?: return null,
//                object : TypeToken<UserDetailsResponse>() {}.type
//            )
//        }
//        set(value) {
//            sharedPreferences.edit() { putString(UserDetails, Gson().toJson(value)) }
//        }

}