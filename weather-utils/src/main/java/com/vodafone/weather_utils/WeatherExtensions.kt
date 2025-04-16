package com.vodafone.weather_utils

import kotlin.math.roundToInt

object WeatherExtensions {

    fun Double.asFormattedTemperature(): String {
        val temperature = this.roundToInt()
        return "$temperature°C"
    }

    fun Double.asFormattedWindSpeed(): String {
        return "${this.toInt()} km/h"
    }
}