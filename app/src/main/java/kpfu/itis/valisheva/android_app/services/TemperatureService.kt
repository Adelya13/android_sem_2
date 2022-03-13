package kpfu.itis.valisheva.android_app.services

import android.graphics.Color
import kpfu.itis.valisheva.android_app.R



class TemperatureService() {

    fun changeTempColour(temp: Double): Int {
        return when(convertToDegrees(temp)){
            in -Int.MAX_VALUE..-20 -> R.color.purple_700
            in -20..-10 -> R.color.blue
            in -10..0 -> R.color.light_green
            in 0..10 -> R.color.bright_green
            in 10..Int.MAX_VALUE -> R.color.bright_red
            else -> R.color.white
        }
    }

    fun convertToDegrees(temp: Double): Int{
        return (temp-273.15).toInt()
    }


}
