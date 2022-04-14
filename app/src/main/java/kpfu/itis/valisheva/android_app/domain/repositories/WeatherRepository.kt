package kpfu.itis.valisheva.android_app.domain.repositories

import kpfu.itis.valisheva.android_app.domain.entities.City
import kpfu.itis.valisheva.android_app.domain.entities.FullCityWeather
import kpfu.itis.valisheva.android_app.domain.entities.ShortCityWeather

interface WeatherRepository {

    suspend fun getCityWeatherById(id: Int) : FullCityWeather
    suspend fun getWeather(city: String): City
    suspend fun getNearCitiesWeather(lat:Double,lon:Double): ArrayList<ShortCityWeather>
}
