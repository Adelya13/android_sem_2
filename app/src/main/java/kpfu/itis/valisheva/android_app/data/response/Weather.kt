package kpfu.itis.valisheva.android_app.data.response

import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val shortDescription : String,
    @SerializedName("description")
    val fullDescription: String,
    @SerializedName("icon")
    val iconID : String,
)
