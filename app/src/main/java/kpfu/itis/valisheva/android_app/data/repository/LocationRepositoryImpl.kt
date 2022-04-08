package kpfu.itis.valisheva.android_app.data.repository

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.UiContext
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.qualifiers.ApplicationContext
import kpfu.itis.valisheva.android_app.domain.entities.Coordinates
import kpfu.itis.valisheva.android_app.domain.repositories.LocationRepository
import javax.inject.Inject
import javax.inject.Named

private const val DEFAULT_LATITUDE = 52.3154
private const val DEFAULT_LONGITUDE = 54.9044

class LocationRepositoryImpl @Inject constructor(
    @ApplicationContext context: Context
) : LocationRepository{



    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private var coordinates : Coordinates = Coordinates(DEFAULT_LATITUDE, DEFAULT_LONGITUDE)

    @SuppressLint("MissingPermission")
    override fun getLocation(): Coordinates {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    coordinates = Coordinates(location.latitude,location.longitude)
                }
            }
        return coordinates
    }
    override fun getDefaultLocation(): Coordinates {
        return coordinates
    }
}
