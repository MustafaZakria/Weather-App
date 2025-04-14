package com.vodafone.data.local.util

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vodafone.data.local.model.CityEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private const val CITY_JSON_URI = "city.json"

class CityJsonLoader @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun parseCityJson(): List<CityEntity> {
        var cities = listOf<CityJson>()
        context.assets.open(CITY_JSON_URI).use { inputStream ->
            val type = object : TypeToken<List<CityJson>>() {}.type
            cities = Gson().fromJson(
                inputStream.reader(),
                type
            )
        }
        return cities.map { it.toCityEntity() }
    }
}