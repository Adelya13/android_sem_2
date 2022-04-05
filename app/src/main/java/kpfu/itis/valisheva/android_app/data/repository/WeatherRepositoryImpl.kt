package kpfu.itis.valisheva.android_app.data.repository

import kpfu.itis.valisheva.android_app.BuildConfig
import kpfu.itis.valisheva.android_app.data.api.Api
import kpfu.itis.valisheva.android_app.data.api.mappers.WeatherMapper
import kpfu.itis.valisheva.android_app.domain.entities.City
import kpfu.itis.valisheva.android_app.domain.entities.FullCityWeather
import kpfu.itis.valisheva.android_app.domain.entities.ShortCityWeather
import kpfu.itis.valisheva.android_app.domain.repositories.WeatherRepository
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


class WeatherRepositoryImpl @Inject constructor(
    private val api: Api,
    private val weatherMapper : WeatherMapper
) : WeatherRepository {

    override suspend fun getWeather(city: String): City {
        return weatherMapper.mapCity(api.getWeather(city))
    }
    override suspend fun getNearCitiesWeather(lat:Double,lon:Double): ArrayList<ShortCityWeather> {
        return weatherMapper.mapCity(api.getWeatherInNearCities(lat,lon,10))
    }

    override suspend fun getCityWeatherById(id: Int) : FullCityWeather {
        return  weatherMapper.mapCity(api.getWeatherById(id))
    }
}
