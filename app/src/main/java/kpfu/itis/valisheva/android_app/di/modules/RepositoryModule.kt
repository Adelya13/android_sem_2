package kpfu.itis.valisheva.android_app.di.modules

import dagger.Binds
import dagger.Module
import kpfu.itis.valisheva.android_app.data.repository.LocationRepositoryImpl
import kpfu.itis.valisheva.android_app.data.repository.WeatherRepositoryImpl
import kpfu.itis.valisheva.android_app.domain.repositories.LocationRepository
import kpfu.itis.valisheva.android_app.domain.repositories.WeatherRepository

@Module
interface RepositoryModule {

    @Binds
    fun weatherRepository(
        impl: WeatherRepositoryImpl
    ): WeatherRepository

    @Binds
    fun locationRepository(
        impl: LocationRepositoryImpl
    ): LocationRepository
}
