package kpfu.itis.valisheva.android_app.services

import android.graphics.Color
import kpfu.itis.valisheva.android_app.R


class TemperatureService(private val temp: Double) {

    fun changeTempColour(): Int {
        return when(temp){
            in -Double.MAX_VALUE..-20.0 -> R.color.purple_700
            in -20.0..-10.0 -> R.color.blue
            in -10.0..0.0 -> R.color.light_green
            in 0.0..10.0 -> R.color.bright_green
            in 10.0..Double.MAX_VALUE -> R.color.bright_red
            else -> R.color.white
        }
    }
}
