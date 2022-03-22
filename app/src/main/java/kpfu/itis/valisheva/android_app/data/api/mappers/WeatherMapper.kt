package kpfu.itis.valisheva.android_app.data.api.mappers

import kpfu.itis.valisheva.android_app.data.response.main.NearWeatherResponse
import kpfu.itis.valisheva.android_app.data.response.main.WeatherResponse
import kpfu.itis.valisheva.android_app.data.response.main.WeatherShortResponse
import kpfu.itis.valisheva.android_app.domain.entities.City
import kpfu.itis.valisheva.android_app.domain.entities.FullCityWeather
import kpfu.itis.valisheva.android_app.domain.entities.ShortCityWeather

class WeatherMapper {


    fun mapCity(response: WeatherShortResponse): City = City(
        id = response.id,
    )

    fun mapCity(response: NearWeatherResponse): ArrayList<ShortCityWeather> =
        covert(response.cities)


    fun mapCity(response: WeatherResponse): FullCityWeather = FullCityWeather(
        id = response.id,
        name = response.name,
        temp = response.baseDescription.temperature,
        feelTemperature = response.baseDescription.feelTemperature,
        maxTemperature= response.baseDescription.maxTemperature,
        minTemperature = response.baseDescription.minTemperature,
        windDegrees = response.wind.degrees,
        humidity = response.baseDescription.humidity,
        fullDescription = response.weather[0].fullDescription,
        windSpeed = response.wind.speed,
        iconId = response.weather[0].iconID
    )

    private fun covert(respCities : ArrayList<WeatherResponse>): ArrayList<ShortCityWeather>{
        var cities: ArrayList<ShortCityWeather> = arrayListOf()
        respCities.forEach{
            cities.add(
                ShortCityWeather(
                    id = it.id,
                    name = it.name,
                    temp = it.baseDescription.temperature
                )
            )
        }
        return cities
    }
}

