package com.vodafone.data.local.sharedpref

import android.content.SharedPreferences
import javax.inject.Inject

class CitySharedPreference @Inject constructor(
    val sharedPreferences: SharedPreferences
) {
    fun saveCityId(id: Int) {
        sharedPreferences.edit().putInt("city", id).apply()
    }

    fun getCityId(): Int {
        return sharedPreferences.getInt("city", -1)
    }
}