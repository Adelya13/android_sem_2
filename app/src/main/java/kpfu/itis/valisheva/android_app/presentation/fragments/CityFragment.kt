package kpfu.itis.valisheva.android_app.presentation.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.FutureTarget
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import kpfu.itis.valisheva.android_app.R
import kpfu.itis.valisheva.android_app.data.api.mappers.WeatherMapper
import kpfu.itis.valisheva.android_app.data.repository.LocationRepositoryImpl
import kpfu.itis.valisheva.android_app.data.repository.WeatherRepositoryImpl
import kpfu.itis.valisheva.android_app.databinding.FragmentCityBinding
import kpfu.itis.valisheva.android_app.domain.coverters.NavigationConverter
import kpfu.itis.valisheva.android_app.domain.entities.FullCityWeather
import kpfu.itis.valisheva.android_app.domain.helpers.UnitHelper
import kpfu.itis.valisheva.android_app.domain.usecases.location.GetDefaultLocationUseCase
import kpfu.itis.valisheva.android_app.domain.usecases.location.GetLocationUseCase
import kpfu.itis.valisheva.android_app.domain.usecases.weather.GetNearCitiesWeatherUseCase
import kpfu.itis.valisheva.android_app.domain.usecases.weather.GetWeatherByIdUseCase
import kpfu.itis.valisheva.android_app.domain.usecases.weather.GetWeatherUseCase
import kpfu.itis.valisheva.android_app.presentation.services.TemperatureService
import kpfu.itis.valisheva.android_app.presentation.viewmodels.CityModelView
import kpfu.itis.valisheva.android_app.presentation.viewmodels.FirstModelView
import kpfu.itis.valisheva.android_app.utils.CityFragmentViewModelsFactory
import kpfu.itis.valisheva.android_app.utils.FirstFragmentViewModelFactory


private const val KEY_CITY_ID = "CITY ID"
private const val IMG_START_URL = "https://openweathermap.org/img/wn/"
private const val IMG_END_URL = "@2x.png"

class CityFragment : Fragment(R.layout.fragment_city){

    private lateinit var viewModel: CityModelView
    private lateinit var binding: FragmentCityBinding
    private lateinit var temperatureService: TemperatureService
    private lateinit var navigationConverter: NavigationConverter
    private lateinit var unitHelper: UnitHelper



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCityBinding.bind(view)
        init()
        getCityWeatherArgumentsById()
    }

    private fun init(){
        initFactory()
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

    private fun initFactory(){
        val getWeatherByIdUseCase = GetWeatherByIdUseCase(
            weatherRepository = WeatherRepositoryImpl(
                weatherMapper = WeatherMapper()
            )
        )
        val factory = CityFragmentViewModelsFactory(
           getWeatherByIdUseCase
        )
        viewModel = ViewModelProvider(
            viewModelStore,
            factory
        )[CityModelView::class.java]
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
