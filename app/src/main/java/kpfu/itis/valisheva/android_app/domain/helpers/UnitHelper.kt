package kpfu.itis.valisheva.android_app.domain.helpers


private const val HUMIDITY_UNIT = "%"
private const val SPEED_UNIT = "m/s"
private const val TEMP_UNIT = "°"

class UnitHelper {

    fun addSpeedUnit(speed: Double): String {
        return speed.toString() + SPEED_UNIT
    }
    fun addHumidityUnit(humidity: Int): String{
        return humidity.toString() + HUMIDITY_UNIT
    }
    fun addTempUnit(temp: Int): String{
        return temp.toString() + TEMP_UNIT
    }
}
