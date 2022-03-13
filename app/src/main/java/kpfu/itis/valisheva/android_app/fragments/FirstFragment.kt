package kpfu.itis.valisheva.android_app.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import kpfu.itis.valisheva.android_app.R
import kpfu.itis.valisheva.android_app.data.repository.WeatherRepository
import kpfu.itis.valisheva.android_app.data.response.NearWeatherResponse
import kpfu.itis.valisheva.android_app.data.response.WeatherResponse
import kpfu.itis.valisheva.android_app.databinding.FragmentFirstBinding
import kpfu.itis.valisheva.android_app.decorators.SpaceItemDecorator
import kpfu.itis.valisheva.android_app.models.City
import kpfu.itis.valisheva.android_app.rv.CityAdapter
import kpfu.itis.valisheva.android_app.services.LocationService
import kotlin.collections.ArrayList

private const val KEY_CITY_ID = "CITY ID"

class FirstFragment : Fragment(R.layout.fragment_first) {


    private lateinit var coordinates: Pair<Double,Double>
    private lateinit var locationService: LocationService
    private lateinit var binding: FragmentFirstBinding
    private lateinit var cityAdapter: CityAdapter

    private val repository by lazy {
        WeatherRepository()
    }

    @SuppressLint("MissingPermission")
    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            coordinates = if (
                it[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                it[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            ) {
                locationService.getLocation()
            }else{
                locationService.getDefaultLocation()
            }
            updateCities()
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFirstBinding.bind(view)
        locationService = LocationService(requireContext())
        initRV()
        initSV()
        getLocation()
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

    private fun updateCities() : ArrayList<City>{
        val citiesList: ArrayList<City> = ArrayList()
        lifecycleScope.launch {
            try {

                val response: NearWeatherResponse = repository.getNearCitiesWeather(
                    coordinates.first,
                    coordinates.second
                )
                response.cities.forEach {
                    val city = City(it.id, it.name, it.baseDescription.temperature)
                    citiesList.add(city)
                }
                initAdapter(citiesList)

            } catch (ex: Exception) {
                showMessage("Search near cities")
                Log.e("SEARCH_EXCEPTION", ex.message.toString())
            }
        }
        return citiesList
    }

    private fun initAdapter(citiesList: ArrayList<City>){
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
                val response: WeatherResponse = repository.getWeather(city)
                openCityWeather(response.id)
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
