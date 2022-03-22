package kpfu.itis.valisheva.android_app.data.response.main

import com.google.gson.annotations.SerializedName

data class NearWeatherResponse(
    @SerializedName("cod")
    val statusCode: Int,
    @SerializedName("list")
    val cities : ArrayList<WeatherResponse>

)
