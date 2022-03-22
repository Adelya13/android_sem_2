package kpfu.itis.valisheva.android_app.domain.coverters


import kpfu.itis.valisheva.android_app.domain.helpers.NavigationConstants.EAST
import kpfu.itis.valisheva.android_app.domain.helpers.NavigationConstants.EEN
import kpfu.itis.valisheva.android_app.domain.helpers.NavigationConstants.ESE
import kpfu.itis.valisheva.android_app.domain.helpers.NavigationConstants.NE
import kpfu.itis.valisheva.android_app.domain.helpers.NavigationConstants.NNE
import kpfu.itis.valisheva.android_app.domain.helpers.NavigationConstants.NORTH
import kpfu.itis.valisheva.android_app.domain.helpers.NavigationConstants.NW
import kpfu.itis.valisheva.android_app.domain.helpers.NavigationConstants.NWN
import kpfu.itis.valisheva.android_app.domain.helpers.NavigationConstants.SE
import kpfu.itis.valisheva.android_app.domain.helpers.NavigationConstants.SOUTH
import kpfu.itis.valisheva.android_app.domain.helpers.NavigationConstants.SSE
import kpfu.itis.valisheva.android_app.domain.helpers.NavigationConstants.SSW
import kpfu.itis.valisheva.android_app.domain.helpers.NavigationConstants.SW
import kpfu.itis.valisheva.android_app.domain.helpers.NavigationConstants.WEST
import kpfu.itis.valisheva.android_app.domain.helpers.NavigationConstants.WNW
import kpfu.itis.valisheva.android_app.domain.helpers.NavigationConstants.WSW


class NavigationConverter(private val degrees: Int) {

    fun formatNavigation(): String{
        return when(degrees){
            360 or 0 -> NORTH
            270 -> WEST
            90 -> EAST
            180 -> SOUTH
            45 -> NE
            135 -> SE
            225 -> SW
            315 -> NW
            in 46..89 -> EEN
            in 1..44 -> NNE
            in 91..134 -> ESE
            in 136..179 -> SSE
            in 181..224 -> SSW
            in 226..269 -> WSW
            in 271..314 -> WNW
            in 316..359 -> NWN
            else -> ""
        }
    }
}
