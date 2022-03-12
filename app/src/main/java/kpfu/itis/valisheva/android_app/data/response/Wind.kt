package kpfu.itis.valisheva.android_app.data.response

import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("speed")
    val speed: Double,
    @SerializedName("deg")
    val degrees: Int,
)
