package kpfu.itis.valisheva.android_app.data.api


import kpfu.itis.valisheva.android_app.data.response.NearWeatherResponse
import kpfu.itis.valisheva.android_app.data.response.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Api{

    @GET("weather")
    suspend fun getWeather(
        @Query("q") city: String
    ): WeatherResponse

    @GET("weather")
    suspend fun getWeatherById(
        @Query("id") id : Int
    ): WeatherResponse

    @GET("find")
    suspend fun getWeatherInNearCities(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("cnt") count: Int
    ): NearWeatherResponse


}
