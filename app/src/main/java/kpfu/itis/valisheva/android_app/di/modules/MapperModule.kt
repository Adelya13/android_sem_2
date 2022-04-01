package kpfu.itis.valisheva.android_app.di.modules

import dagger.Module
import dagger.Provides
import kpfu.itis.valisheva.android_app.data.api.mappers.WeatherMapper

@Module
class MapperModule {
    @Provides
    fun provideWeatherMapper(): WeatherMapper = WeatherMapper()
}
