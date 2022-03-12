package kpfu.itis.valisheva.android_app.data.response

import com.google.gson.annotations.SerializedName

data class NearWeatherResponse(
    @SerializedName("cod")
    val statusCode: Int,
    @SerializedName("list")
    val cities : ArrayList<WeatherResponse>

)
