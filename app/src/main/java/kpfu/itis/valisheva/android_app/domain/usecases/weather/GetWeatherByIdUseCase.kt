package kpfu.itis.valisheva.android_app.domain.usecases.weather

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kpfu.itis.valisheva.android_app.domain.entities.City
import kpfu.itis.valisheva.android_app.domain.entities.FullCityWeather
import kpfu.itis.valisheva.android_app.domain.repositories.WeatherRepository

class GetWeatherByIdUseCase (
    private val weatherRepository: WeatherRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
){
    suspend operator fun invoke(id: Int): FullCityWeather {
        return withContext(dispatcher){
            weatherRepository.getCityWeatherById(id)
        }
    }
}
