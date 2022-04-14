package kpfu.itis.valisheva.android_app.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kpfu.itis.valisheva.android_app.domain.entities.City
import kpfu.itis.valisheva.android_app.domain.entities.Coordinates
import kpfu.itis.valisheva.android_app.domain.entities.ShortCityWeather
import kpfu.itis.valisheva.android_app.domain.usecases.location.GetDefaultLocationUseCase
import kpfu.itis.valisheva.android_app.domain.usecases.location.GetLocationUseCase
import kpfu.itis.valisheva.android_app.domain.usecases.weather.GetNearCitiesWeatherUseCase
import kpfu.itis.valisheva.android_app.domain.usecases.weather.GetWeatherUseCase
import javax.inject.Inject

@HiltViewModel
class FirstModelView @Inject constructor(
    private val getLocationUseCase: GetLocationUseCase,
    private val getDefaultLocationUseCase: GetDefaultLocationUseCase,
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getNearCitiesWeatherUseCase: GetNearCitiesWeatherUseCase
): ViewModel() {

    private var _city: SingleLiveEvent<Result<City>> = SingleLiveEvent<Result<City>>()
    val city: SingleLiveEvent<Result<City>> = _city

    private var _coordinates: MutableLiveData<Result<Coordinates>> = MutableLiveData()
    val coordinates: LiveData<Result<Coordinates>> = _coordinates

    private var _nearCities: MutableLiveData<Result<ArrayList<ShortCityWeather>>> = MutableLiveData()
    val nearCities: LiveData<Result<ArrayList<ShortCityWeather>>> = _nearCities

    fun onGetWeatherClick(query: String){
        viewModelScope.launch {
            try{
                val city = getWeatherUseCase(query)
                _city.value = Result.success(city)
            }catch (ex: Exception) {
                _city.value = Result.failure(ex)
            }

        }
    }

    fun getLocation(){
        viewModelScope.launch {
            try{
                val coordinates =  getLocationUseCase.invoke()
                _coordinates.value = Result.success(coordinates)
            }catch(ex: Exception){
                _city.value = Result.failure(ex)
            }
        }
    }
    fun getDefaultLocation(){
        viewModelScope.launch {
            try{
                val coordinates =  getDefaultLocationUseCase.invoke()
                _coordinates.value = Result.success(coordinates)
            }catch(ex: Exception){
                _city.value = Result.failure(ex)
            }
        }
    }

    fun getNearCitiesWeather(coordinates: Coordinates){
        viewModelScope.launch {
            try{
                var citiesList: ArrayList<ShortCityWeather> = getNearCitiesWeatherUseCase(
                    coordinates.latitude,
                    coordinates.longitude
                )
                _nearCities.value = Result.success(citiesList)
            }catch(ex: Exception){
                _nearCities.value = Result.failure(ex)
            }
        }

    }



}
