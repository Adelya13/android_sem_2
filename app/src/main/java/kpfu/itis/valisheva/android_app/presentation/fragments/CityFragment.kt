package kpfu.itis.valisheva.android_app.presentation.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.FutureTarget
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import kpfu.itis.valisheva.android_app.R
import kpfu.itis.valisheva.android_app.data.api.mappers.WeatherMapper
import kpfu.itis.valisheva.android_app.data.repository.WeatherRepositoryImpl
import kpfu.itis.valisheva.android_app.databinding.FragmentCityBinding
import kpfu.itis.valisheva.android_app.domain.coverters.NavigationConverter
import kpfu.itis.valisheva.android_app.domain.entities.FullCityWeather
import kpfu.itis.valisheva.android_app.domain.helpers.UnitHelper
import kpfu.itis.valisheva.android_app.domain.usecases.weather.GetWeatherByIdUseCase
import kpfu.itis.valisheva.android_app.presentation.services.TemperatureService


private const val KEY_CITY_ID = "CITY ID"
private const val IMG_START_URL = "https://openweathermap.org/img/wn/"
private const val IMG_END_URL = "@2x.png"

class CityFragment : Fragment(R.layout.fragment_city){

    private lateinit var getWeatherByIdUseCase: GetWeatherByIdUseCase
    private lateinit var binding: FragmentCityBinding
    private lateinit var temperatureService: TemperatureService
    private lateinit var navigationConverter: NavigationConverter
    private lateinit var unitHelper: UnitHelper



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCityBinding.bind(view)
        initUseCase()
        temperatureService = TemperatureService()
        unitHelper = UnitHelper()
        getCityWeatherArgumentsById()
    }


    private fun initUseCase(){
        getWeatherByIdUseCase = GetWeatherByIdUseCase(
            weatherRepository = WeatherRepositoryImpl(
                weatherMapper = WeatherMapper()
            )

        )
    }

    private fun getCityWeatherArgumentsById(){
        searchCityWeatherById(
            arguments?.get(
                KEY_CITY_ID
            ) as Int
        )

    }

    private fun searchCityWeatherById(id: Int) {
        lifecycleScope.launch {
            try {
                val city = getWeatherByIdUseCase(id)
                showWeatherAttributes(city)
            } catch (ex: Exception) {
                showMessage("Something went wrong")
                Log.e("SEARCH_EXCEPTION", ex.message.toString())
            }
        }
    }

    private fun showWeatherAttributes(city: FullCityWeather){
        with(binding){
            with(city){
                convertTemp(temp, tvTempInsert)
                convertTemp(feelTemperature, tvFeelLikeValue)
                convertTemp(maxTemperature, tvMaxTempValue)
                convertTemp(minTemperature,tvMinTempValue)
                navigationConverter = NavigationConverter(windDegrees)
                tvNavigationValue.text = navigationConverter.formatNavigation()
                tvCity.text = name
                tvHumidityValue.text = unitHelper.addHumidityUnit(humidity)
                tvWeatherDescription.text = fullDescription
                tvWindSpeedValue.text = unitHelper.addSpeedUnit(windSpeed)
                downloadImg(iconId)
            }
        }
    }

    private fun downloadImg(weatherIcon: String){
        lifecycleScope.launch {
            Glide.with(this@CityFragment).load(
                IMG_START_URL + weatherIcon + IMG_END_URL
            ).into(binding.ivWeather)
        }
    }

    private fun convertTemp(temp : Double, tv: TextView){
        tv.text =  unitHelper.addTempUnit(
            temperatureService.convertToDegrees(
                temp
            )
        )
        tv.setTextColor(temperatureService.changeTempColour(temp))
    }

    private fun showMessage(message: String) {
        Snackbar.make(
            requireActivity().findViewById(R.id.container),
            message,
            Snackbar.LENGTH_LONG
        ).show()
    }
}
