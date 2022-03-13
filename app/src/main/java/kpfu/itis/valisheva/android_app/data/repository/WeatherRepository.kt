package kpfu.itis.valisheva.android_app.data.repository

import kpfu.itis.valisheva.android_app.BuildConfig
import kpfu.itis.valisheva.android_app.data.api.Api
import kpfu.itis.valisheva.android_app.data.response.NearWeatherResponse
import kpfu.itis.valisheva.android_app.data.response.WeatherResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
private const val API_KEY = "56fc6c6cb76c0864b4cd055080568268"
private const val QUERY_API_KEY = "appid"

class WeatherRepository{

    private val apiKeyInterceptor = Interceptor { chain ->
        val original = chain.request()
        val newURL = original.url.newBuilder()
            .addQueryParameter(QUERY_API_KEY, API_KEY)
            .build()

        chain.proceed(
            original.newBuilder()
                .url(newURL)
                .build()
        )
    }

    private val okhttp: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .also {
                if (BuildConfig.DEBUG) {
                    it.addInterceptor(
                        HttpLoggingInterceptor()
                            .setLevel(
                                HttpLoggingInterceptor.Level.BODY
                            )
                    )
                }
            }
            .build()
    }

    private val api: Api by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okhttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }

    suspend fun getWeather(city: String): WeatherResponse {
        return api.getWeather(city)
    }
    suspend fun getNearCitiesWeather(lat:Double,lon:Double): NearWeatherResponse {
        return api.getWeatherInNearCities(lat,lon,10)
    }
    suspend fun getCityWeatherById(id: Int) : WeatherResponse {
        return  api.getWeatherById(id)
    }
}
