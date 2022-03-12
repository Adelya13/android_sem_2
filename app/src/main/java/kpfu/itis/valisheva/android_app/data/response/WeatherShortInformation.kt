package kpfu.itis.valisheva.android_app.data.response

import com.google.gson.annotations.SerializedName

data class WeatherShortInformation(
    @SerializedName("id")
    val id :Int,
    @SerializedName("main")
    val baseDescription: BaseDescription,
)
