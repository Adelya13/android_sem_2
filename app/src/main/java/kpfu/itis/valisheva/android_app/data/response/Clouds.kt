package kpfu.itis.valisheva.android_app.data.response

import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all")
    val cloudsPercent: Int,
)
