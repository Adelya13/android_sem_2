package kpfu.itis.valisheva.android_app.domain.usecases.weather

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kpfu.itis.valisheva.android_app.domain.entities.City
import kpfu.itis.valisheva.android_app.domain.repositories.WeatherRepository
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(name: String): City{
        return withContext(dispatcher){
            weatherRepository.getWeather(name)
        }
    }
}
