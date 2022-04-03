package kpfu.itis.valisheva.android_app.presentation.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kpfu.itis.valisheva.android_app.App
import kpfu.itis.valisheva.android_app.R
import kpfu.itis.valisheva.android_app.data.repository.LocationRepositoryImpl
import kpfu.itis.valisheva.android_app.databinding.FragmentFirstBinding
import kpfu.itis.valisheva.android_app.domain.entities.Coordinates
import kpfu.itis.valisheva.android_app.presentation.decorators.SpaceItemDecorator
import kpfu.itis.valisheva.android_app.domain.entities.ShortCityWeather
import kpfu.itis.valisheva.android_app.presentation.viewmodels.FirstModelView
import kpfu.itis.valisheva.android_app.presentation.rv.CityAdapter
import kpfu.itis.valisheva.android_app.utils.AppViewModelFactory
import javax.inject.Inject
import kotlin.collections.ArrayList

private const val KEY_CITY_ID = "CITY ID"

class FirstFragment : Fragment(R.layout.fragment_first) {

    private lateinit var coordinates: Coordinates
    private lateinit var binding: FragmentFirstBinding
    private lateinit var cityAdapter: CityAdapter


    @Inject
    lateinit var factory: AppViewModelFactory

    private val viewModel: FirstModelView by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity?.application as App).appComponent.inject(this)
        getLocation()
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("MissingPermission")
    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if (
                it[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                it[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            ) {
                 viewModel.getLocation()
                 showMessage("Location is found")
            }else{
                showMessage("Location don't find, generate defaultLocation")
                viewModel.getDefaultLocation()
            }
            viewModel.getNearCitiesWeather(coordinates)
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFirstBinding.bind(view)
        getLocation()
        initAll()

    }


    private fun initAll(){
        initObservers()
        initRV()
        initSV()
    }

    private fun initObservers(){
        viewModel.city.observe(viewLifecycleOwner){
            it?.fold(onSuccess = { it ->
                openCityWeather(it.id)
            },onFailure = {
                showMessage("Such city not found")
                Log.e("CITY_FIND_EXCEPTION", it.message.toString())
            })
        }
        viewModel.coordinates.observe(viewLifecycleOwner){
            it.fold(onSuccess = {
                coordinates = it
                viewModel.getNearCitiesWeather(it)

            },onFailure = {
                showMessage("Location not found")
                Log.e("LOCATION_EXCEPTION", it.message.toString())
            })
        }
        viewModel.nearCities.observe(viewLifecycleOwner){
            it.fold(onSuccess = {
                initAdapter(it)
            }, onFailure = {
                showMessage("Search near cities")
                Log.e("SEARCH_EXCEPTION", it.message.toString())
            })
        }
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
                        viewModel.onGetWeatherClick(query)
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
