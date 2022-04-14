package kpfu.itis.valisheva.android_app.domain.usecases.weather

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kpfu.itis.valisheva.android_app.domain.entities.FullCityWeather
import kpfu.itis.valisheva.android_app.domain.entities.ShortCityWeather
import kpfu.itis.valisheva.android_app.domain.repositories.WeatherRepository
import javax.inject.Inject

class GetNearCitiesWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
){
    suspend operator fun invoke(lat:Double,lon:Double): ArrayList<ShortCityWeather> {
        return withContext(dispatcher){
            weatherRepository.getNearCitiesWeather(lat,lon)
        }
    }
}
