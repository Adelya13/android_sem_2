package kpfu.itis.valisheva.android_app.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kpfu.itis.valisheva.android_app.domain.usecases.location.GetDefaultLocationUseCase
import kpfu.itis.valisheva.android_app.domain.usecases.location.GetLocationUseCase
import kpfu.itis.valisheva.android_app.domain.usecases.weather.GetNearCitiesWeatherUseCase
import kpfu.itis.valisheva.android_app.domain.usecases.weather.GetWeatherUseCase
import kpfu.itis.valisheva.android_app.presentation.viewmodels.FirstModelView

class FirstFragmentViewModelFactory(
    private val getLocationUseCase: GetLocationUseCase,
    private val getDefaultLocationUseCase: GetDefaultLocationUseCase,
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getNearCitiesWeatherUseCase: GetNearCitiesWeatherUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(FirstModelView::class.java) ->
                FirstModelView(
                    getLocationUseCase,
                    getDefaultLocationUseCase,
                    getWeatherUseCase,
                    getNearCitiesWeatherUseCase) as? T ?: throw IllegalArgumentException("Unknown ViewModel class")
            else ->
                throw IllegalArgumentException("Unknown ViewModel class")
        }
}
