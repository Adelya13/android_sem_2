package kpfu.itis.valisheva.android_app.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import kpfu.itis.valisheva.android_app.App
import kpfu.itis.valisheva.android_app.R
import kpfu.itis.valisheva.android_app.databinding.FragmentCityBinding
import kpfu.itis.valisheva.android_app.domain.coverters.NavigationConverter
import kpfu.itis.valisheva.android_app.domain.entities.FullCityWeather
import kpfu.itis.valisheva.android_app.domain.helpers.UnitHelper
import kpfu.itis.valisheva.android_app.presentation.services.TemperatureService
import kpfu.itis.valisheva.android_app.presentation.viewmodels.CityModelView
import kpfu.itis.valisheva.android_app.utils.AppViewModelFactory
import javax.inject.Inject


private const val KEY_CITY_ID = "CITY ID"
private const val IMG_START_URL = "https://openweathermap.org/img/wn/"
private const val IMG_END_URL = "@2x.png"

class CityFragment : Fragment(R.layout.fragment_city){


    private lateinit var binding: FragmentCityBinding
    private lateinit var temperatureService: TemperatureService
    private lateinit var navigationConverter: NavigationConverter
    private lateinit var unitHelper: UnitHelper

    @Inject
    lateinit var factory: AppViewModelFactory

    private val viewModel: CityModelView by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity?.application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCityBinding.bind(view)
        init()
        getCityWeatherArgumentsById()
    }

    private fun init(){
        initObservers()
        temperatureService = TemperatureService()
        unitHelper = UnitHelper()
    }

    private fun initObservers(){
        viewModel.city.observe(viewLifecycleOwner){
            it.fold(onSuccess = {city ->
                showWeatherAttributes(city)
            },onFailure = {
                showMessage("Something went wrong")
                Log.e("SEARCH_EXCEPTION", it.message.toString())
            })
        }
    }



    private fun getCityWeatherArgumentsById(){
        viewModel.searchCityWeatherById(
            arguments?.get(
                KEY_CITY_ID
            ) as Int
        )

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
