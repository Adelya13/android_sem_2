package kpfu.itis.valisheva.android_app.domain.entities

data class FullCityWeather(
    val id: Int,
    val name: String,
    val temp: Double,
    val feelTemperature: Double,
    val maxTemperature: Double,
    val minTemperature: Double,
    val windDegrees: Int,
    val humidity: Int,
    val fullDescription: String,
    val windSpeed: Double,
    val iconId: String,
)
