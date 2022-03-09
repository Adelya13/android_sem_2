package kpfu.itis.valisheva.android_app.data.response

import com.google.gson.annotations.SerializedName

data class CitiesWeatherListResponce(
    @SerializedName("cod")
    val statusCod: Int,
    @SerializedName("list")
    val nearCities: ArrayList<WeatherResponse>
)
