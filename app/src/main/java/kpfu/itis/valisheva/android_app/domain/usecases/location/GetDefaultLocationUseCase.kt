package kpfu.itis.valisheva.android_app.domain.usecases.location

import kpfu.itis.valisheva.android_app.domain.entities.Coordinates
import kpfu.itis.valisheva.android_app.domain.repositories.LocationRepository
import javax.inject.Inject

class GetDefaultLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
){
    operator fun invoke(): Coordinates {
        return locationRepository.getDefaultLocation()
    }
}
