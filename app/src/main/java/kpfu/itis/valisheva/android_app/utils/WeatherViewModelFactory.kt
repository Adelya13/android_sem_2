package kpfu.itis.valisheva.android_app.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kpfu.itis.valisheva.android_app.domain.usecases.location.GetDefaultLocationUseCase
import kpfu.itis.valisheva.android_app.domain.usecases.location.GetLocationUseCase
import kpfu.itis.valisheva.android_app.domain.usecases.weather.GetNearCitiesWeatherUseCase
import kpfu.itis.valisheva.android_app.domain.usecases.weather.GetWeatherByIdUseCase
import kpfu.itis.valisheva.android_app.domain.usecases.weather.GetWeatherUseCase
import kpfu.itis.valisheva.android_app.presentation.viewmodels.CityModelView
import kpfu.itis.valisheva.android_app.presentation.viewmodels.FirstModelView
import javax.inject.Inject
import javax.inject.Provider

class WeatherViewModelFactory @Inject constructor(
    private val viewModelMap: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val result = viewModelMap[modelClass] ?: viewModelMap.entries.firstOrNull {
            modelClass.isAssignableFrom(it.key)
        }?.value ?: throw IllegalArgumentException("Unknown model class $modelClass")
        @Suppress("UNCHECKED_CAST")
        return result.get() as T
    }
}
