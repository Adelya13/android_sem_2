package kpfu.itis.valisheva.android_app.domain.usecases.location

import kpfu.itis.valisheva.android_app.domain.entities.Coordinates
import kpfu.itis.valisheva.android_app.domain.repositories.LocationRepository

class GetDefaultLocationUseCase(
    private val locationRepository: LocationRepository
){
    operator fun invoke(): Coordinates {
        return locationRepository.getDefaultLocation()
    }
}
