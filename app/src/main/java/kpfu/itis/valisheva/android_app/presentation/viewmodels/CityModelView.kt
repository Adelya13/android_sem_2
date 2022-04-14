package kpfu.itis.valisheva.android_app.presentation.viewmodels

import androidx.lifecycle.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kpfu.itis.valisheva.android_app.domain.entities.FullCityWeather
import kpfu.itis.valisheva.android_app.domain.usecases.weather.GetWeatherByIdUseCase
import javax.inject.Inject

@HiltViewModel
class CityModelView @Inject constructor(
    private val getWeatherByIdUseCase: GetWeatherByIdUseCase
): ViewModel() {

    private var _city: MutableLiveData<Result<FullCityWeather>> = MutableLiveData()
    val city: LiveData<Result<FullCityWeather>> = _city

    private var _error: MutableLiveData<Exception> = MutableLiveData()
    val error: LiveData<Exception> = _error


    fun searchCityWeatherById(id: Int) {
        viewModelScope.launch {
            try {
                val city = getWeatherByIdUseCase(id)
                _city.value = Result.success(city)
            }catch (ex: Exception) {
                _city.value = Result.failure(ex)

                _error.value = ex
            }
        }
    }

}
