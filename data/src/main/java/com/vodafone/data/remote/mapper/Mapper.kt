package com.vodafone.data.remote.mapper

import com.vodafone.core.domain.model.Daily
import com.vodafone.core.domain.model.Weather
import com.vodafone.core.domain.model.WeatherDetail
import com.vodafone.data.remote.dto.ForecastDay
import com.vodafone.data.remote.dto.WeatherDetailDto
import com.vodafone.data.remote.dto.WeatherDto

fun WeatherDto.toWeather(): Weather {
    return Weather(
        city = location.name,
        lat = location.lat,
        lon = location.lon,
        temperature = current.temp_c.asFormattedTemperature(),
        icon = current.condition.icon.asFormattedIconUrl(),
        date = location.localtime_epoch.asCurrentDate(),
        condition = current.condition.text
    )
}

fun WeatherDetailDto.toWeatherDetail(): WeatherDetail {
    return WeatherDetail(
        cityName = location.name,
        lat = location.lat,
        lon = location.lon,
        temperature = current.temp_c.asFormattedTemperature(),
        icon = current.condition.icon.asFormattedIconUrl(),
        date = location.localtime_epoch.asCurrentDate(),
        status = current.condition.text,
        dailyForecast = forecast.forecastday.map { it.asDaily() },
    )
}

fun ForecastDay.asDaily(): Daily {
    return Daily(
        avgTemperature = day.avgtemp_c.asFormattedTemperature(),
        minTemperature = day.mintemp_c.asFormattedTemperature(),
        maxTemperature = day.maxtemp_c.asFormattedTemperature(),
        icon = day.condition.icon.asFormattedIconUrl(),
        date = date_epoch.asDailyDate(),
        status = day.condition.text
    )
}