package kpfu.itis.valisheva.android_app.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kpfu.itis.valisheva.android_app.domain.usecases.weather.GetWeatherByIdUseCase
import kpfu.itis.valisheva.android_app.presentation.viewmodels.CityModelView

class CityFragmentViewModelsFactory(
    private val getWeatherByIdUseCase: GetWeatherByIdUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(CityModelView::class.java) ->
                CityModelView(
                    getWeatherByIdUseCase
                ) as? T ?: throw IllegalArgumentException("Unknown ViewModel class")
            else ->
                throw IllegalArgumentException("Unknown ViewModel class")
        }
}

