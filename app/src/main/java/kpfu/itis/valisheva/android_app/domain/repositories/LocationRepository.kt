package kpfu.itis.valisheva.android_app.domain.repositories

import kpfu.itis.valisheva.android_app.domain.entities.Coordinates

interface LocationRepository {

    fun getLocation(): Coordinates
    fun getDefaultLocation(): Coordinates
}
