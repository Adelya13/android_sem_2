package kpfu.itis.valisheva.android_app.presentation.viewmodels

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import kpfu.itis.valisheva.android_app.domain.entities.FullCityWeather
import kpfu.itis.valisheva.android_app.domain.usecases.weather.GetWeatherByIdUseCase

class CityModelView(
    private val getWeatherByIdUseCase: GetWeatherByIdUseCase
): ViewModel() {

    private var _city: MutableLiveData<Result<FullCityWeather>> = MutableLiveData()
    val city: LiveData<Result<FullCityWeather>> = _city


    fun searchCityWeatherById(id: Int) {
        viewModelScope.launch {
            try {
                val city = getWeatherByIdUseCase(id)
                _city.value = Result.success(city)
            }catch (ex: Exception) {
                _city.value = Result.failure(ex)
            }
        }
    }

}
