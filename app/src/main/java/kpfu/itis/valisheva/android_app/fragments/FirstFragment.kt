package kpfu.itis.valisheva.android_app.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import kpfu.itis.valisheva.android_app.R
import kpfu.itis.valisheva.android_app.databinding.FragmentFirstBinding
import kpfu.itis.valisheva.android_app.exceptions.NotFoundLocationException
import kpfu.itis.valisheva.android_app.services.LocationService


class FirstFragment : Fragment(R.layout.fragment_first) {


    private lateinit var coordinates: Pair<Double,Double>
    private lateinit var locationService: LocationService
    private lateinit var binding: FragmentFirstBinding

    @SuppressLint("MissingPermission")
    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if (it[Manifest.permission.ACCESS_FINE_LOCATION] == true || it[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
                try{
                    coordinates = locationService.getLocation()
                }catch (ex: NotFoundLocationException){
                    showMessage(ex.message.toString())
                }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locationService = LocationService(requireContext())
        binding = FragmentFirstBinding.bind(view)
        getLocation()
    }

    override fun onResume() {
        super.onResume()
        getLocation()
    }


    private fun getLocation() {
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
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
