package kpfu.itis.valisheva.android_app.data.response

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("id")
    val id :Int,
    @SerializedName("coord")
    val coordinates: Coordinates,
    @SerializedName("weather")
    val weather: Weather,
    @SerializedName("main")
    val baseDescription: BaseDescription,
    @SerializedName("wind")
    val wind: Wind,
    @SerializedName("clouds")
    val cloudsPercent: Clouds,
    @SerializedName("sys")
    val systemDescription: SystemDescription,
    @SerializedName("name")
    val name: String,
    @SerializedName("cod")
    val statusCod: Int,
)



