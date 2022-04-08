package kpfu.itis.valisheva.android_app.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kpfu.itis.valisheva.android_app.data.api.mappers.WeatherMapper

@Module
@InstallIn(SingletonComponent::class)
class MapperModule {
    @Provides
    fun provideWeatherMapper(): WeatherMapper = WeatherMapper()
}
