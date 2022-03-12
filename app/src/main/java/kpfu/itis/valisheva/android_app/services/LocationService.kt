package kpfu.itis.valisheva.android_app.services

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices
import kpfu.itis.valisheva.android_app.exceptions.NotFoundLocationException

private const val DEFAULT_LATITUDE = 52.3154
private const val DEFAULT_LONGITUDE = 54.9044

class LocationService(context: Context){

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private var coordinates : Pair<Double,Double> = Pair(DEFAULT_LATITUDE,DEFAULT_LONGITUDE)

    @SuppressLint("MissingPermission")
    fun getLocation(): Pair<Double,Double> {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    coordinates = Pair(location.latitude,location.longitude)
                }
            }
        return coordinates
    }
    fun getDefaultLocation(): Pair<Double, Double> {
        return coordinates
    }
}
