package kpfu.itis.valisheva.android_app.domain.usecases.location

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kpfu.itis.valisheva.android_app.domain.entities.Coordinates
import kpfu.itis.valisheva.android_app.domain.entities.ShortCityWeather
import kpfu.itis.valisheva.android_app.domain.repositories.LocationRepository
import kpfu.itis.valisheva.android_app.domain.repositories.WeatherRepository
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
){
    operator fun invoke(): Coordinates {
        return locationRepository.getLocation()
    }
}
