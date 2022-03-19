package kpfu.itis.valisheva.android_app.data.response

import com.google.gson.annotations.SerializedName

data class BaseDescription(
    @SerializedName("temp")
    val temperature: Double,
    @SerializedName("feels_like")
    val feelTemperature: Double,
    @SerializedName("temp_min")
    val minTemperature: Double,
    @SerializedName("temp_max")
    val maxTemperature: Double,
    @SerializedName("pressure")
    val pressure: Int,
    @SerializedName("humidity")
    val humidity: Int,
)
