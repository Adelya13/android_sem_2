package kpfu.itis.valisheva.android_app.fragments

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import kpfu.itis.valisheva.android_app.R
import kpfu.itis.valisheva.android_app.data.repository.WeatherRepository
import kpfu.itis.valisheva.android_app.data.response.WeatherResponse
import kpfu.itis.valisheva.android_app.databinding.FragmentCityBinding
import kpfu.itis.valisheva.android_app.helpers.UnitHelper
import kpfu.itis.valisheva.android_app.services.NavigationConverter
import kpfu.itis.valisheva.android_app.services.TemperatureService


private const val KEY_CITY_ID = "CITY ID"
private const val IMG_START_URL = "https://openweathermap.org/img/wn/"
private const val IMG_END_URL = "@2x.png"

class CityFragment : Fragment(R.layout.fragment_city){

    private lateinit var binding: FragmentCityBinding
    private lateinit var temperatureService: TemperatureService
    private lateinit var navigationConverter: NavigationConverter
    private lateinit var unitHelper: UnitHelper
    private val repository by lazy {
        WeatherRepository()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCityBinding.bind(view)
        temperatureService = TemperatureService()
        unitHelper = UnitHelper()
        getCityWeatherArgumentsById()
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
                val response: WeatherResponse = repository.getCityWeatherById(id)
                showWeatherAttributes(response)
            } catch (ex: Exception) {
                showMessage("Something went wrong")
                Log.e("SEARCH_EXCEPTION", ex.message.toString())
            }
        }
    }

    private fun showWeatherAttributes(response: WeatherResponse){
        with(binding){
            with(response){
                convertTemp(baseDescription.temperature, tvTempInsert)
                convertTemp(baseDescription.feelTemperature, tvFeelLikeValue)
                convertTemp(baseDescription.maxTemperature, tvMaxTempValue)
                convertTemp(baseDescription.minTemperature,tvMinTempValue)
                navigationConverter = NavigationConverter(wind.degrees)
                tvNavigationValue.text = navigationConverter.formatNavigation()
                tvCity.text = name
                tvHumidityValue.text = unitHelper.addHumidityUnit(baseDescription.humidity)
                tvWeatherDescription.text = weather[0].fullDescription
                tvWindSpeedValue.text = unitHelper.addSpeedUnit(wind.speed)
                downloadImg(weather[0].iconID)
            }
        }
    }

    private fun downloadImg(weatherIcon: String){
        Glide.with(this).load(
            IMG_START_URL + weatherIcon + IMG_END_URL
        ).into(binding.ivWeather)
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
