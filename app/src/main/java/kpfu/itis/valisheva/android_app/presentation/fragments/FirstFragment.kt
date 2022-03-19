package kpfu.itis.valisheva.android_app.presentation.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import kpfu.itis.valisheva.android_app.R
import kpfu.itis.valisheva.android_app.data.api.mappers.WeatherMapper
import kpfu.itis.valisheva.android_app.data.repository.LocationRepositoryImpl
import kpfu.itis.valisheva.android_app.data.repository.WeatherRepositoryImpl
import kpfu.itis.valisheva.android_app.databinding.FragmentFirstBinding
import kpfu.itis.valisheva.android_app.domain.entities.Coordinates
import kpfu.itis.valisheva.android_app.presentation.decorators.SpaceItemDecorator
import kpfu.itis.valisheva.android_app.domain.entities.ShortCityWeather
import kpfu.itis.valisheva.android_app.domain.usecases.location.GetDefaultLocationUseCase
import kpfu.itis.valisheva.android_app.domain.usecases.location.GetLocationUseCase
import kpfu.itis.valisheva.android_app.domain.usecases.weather.GetNearCitiesWeatherUseCase
import kpfu.itis.valisheva.android_app.domain.usecases.weather.GetWeatherUseCase
import kpfu.itis.valisheva.android_app.presentation.rv.CityAdapter
import kotlin.collections.ArrayList

private const val KEY_CITY_ID = "CITY ID"

class FirstFragment : Fragment(R.layout.fragment_first) {

    private lateinit var getLocationUseCase: GetLocationUseCase
    private lateinit var getDefaultLocationUseCase: GetDefaultLocationUseCase
    private lateinit var getWeatherUseCase: GetWeatherUseCase
    private lateinit var getNearCitiesWeatherUseCase: GetNearCitiesWeatherUseCase
    private lateinit var coordinates: Coordinates
    private lateinit var binding: FragmentFirstBinding
    private lateinit var cityAdapter: CityAdapter



    @SuppressLint("MissingPermission")
    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            coordinates = if (
                it[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                it[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            ) {
                getLocationUseCase.invoke()
            }else{
                getDefaultLocationUseCase.invoke()
            }
            updateCities()
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFirstBinding.bind(view)
        initAll()
        getLocation()
    }


    private fun initAll(){
        initUseCases()
        initRV()
        initSV()
    }

    private fun initUseCases(){
        getLocationUseCase = GetLocationUseCase(
            locationRepository = LocationRepositoryImpl(
                context = requireContext()
            )
        )
        getDefaultLocationUseCase = GetDefaultLocationUseCase(
            locationRepository = LocationRepositoryImpl(
                context = requireContext()
            )
        )
        getWeatherUseCase = GetWeatherUseCase(
            weatherRepository = WeatherRepositoryImpl(
                weatherMapper = WeatherMapper()
            )
        )
        getNearCitiesWeatherUseCase = GetNearCitiesWeatherUseCase(
            weatherRepository = WeatherRepositoryImpl(
                weatherMapper = WeatherMapper()
            )
        )

    }

    private fun initRV(){
        val decorator = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        val spacing = SpaceItemDecorator(requireContext())
        binding.rvCities.run{
            addItemDecoration(decorator)
            addItemDecoration(spacing)
        }
    }
    private fun initSV(){
        with(binding){
            svSearchCity.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null) {
                        getWeather(query)
                    }else{
                        showMessage("Please enter city")
                    }
                    return false
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()
        getLocation()
    }

    private fun updateCities() : ArrayList<ShortCityWeather>{
        var citiesList: ArrayList<ShortCityWeather> = arrayListOf()
        lifecycleScope.launch {
            try {
                citiesList = getNearCitiesWeatherUseCase(
                    coordinates.latitude,
                    coordinates.longitude
                )
                initAdapter(citiesList)
            } catch (ex: Exception) {
                showMessage("Search near cities")
                Log.e("SEARCH_EXCEPTION", ex.message.toString())
            }
        }
        return citiesList
    }

    private fun initAdapter(citiesList: ArrayList<ShortCityWeather>){
        cityAdapter = CityAdapter(citiesList) {
            openCityWeather(it)
        }
        binding.rvCities.apply{
            adapter = cityAdapter
        }
    }

    private fun getLocation() {
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun getWeather(city: String) {
        lifecycleScope.launch {
            try {
                val city = getWeatherUseCase(city)
                openCityWeather(city.id)
            } catch (ex: Exception) {
                showMessage("Such city not found")
                Log.e("SEARCH_EXCEPTION", ex.message.toString())
            }
        }
    }

    private fun openCityWeather(cityId: Int?){
        var bundle: Bundle? = null
        cityId?.let {
            bundle = Bundle().apply {
                putInt(KEY_CITY_ID, cityId)
            }
        }
        val options = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .build()

        findNavController().navigate(
            R.id.action_firstFragment_to_cityFragment,
            bundle,
            options
        )

    }

    private fun showMessage(message: String) {
        Snackbar.make(
            requireActivity().findViewById(R.id.container),
            message,
            Snackbar.LENGTH_LONG
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}
