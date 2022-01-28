package com.example.smartweather.ui.main

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.smartweather.R
import com.example.smartweather.databinding.FragmentFirstBinding
import com.example.smartweather.ui.main.repository.WeatherRepository
import com.example.smartweather.ui.main.viewmodels.WeatherViewModel
import com.google.android.gms.location.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private val LOCATION_PERMISSION_REQUEST = 1
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    lateinit var weatherViewModel: WeatherViewModel

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        weatherViewModel = ViewModelProvider(this,WeatherViewModel.FACTORY(WeatherRepository())).get(WeatherViewModel::class.java)

        binding.lifecycleOwner = this // Add the owner of the view

        binding.viewModel = weatherViewModel

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        /*Check location*/
        getLocationAccess()


    }



    private fun getLocationAccess() {
        if (ContextCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLocationUpdates()
            startLocationUpdates()
        }
        else
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                getLocationUpdates()
                startLocationUpdates()
            }
            else {
                Toast.makeText(requireActivity(), "User has not granted location access permission", Toast.LENGTH_LONG).show()
                requireActivity().finish()
            }
        }
    }




    private fun getLocationUpdates() {
        locationRequest = LocationRequest()
        locationRequest.interval = 100000
        locationRequest.fastestInterval = 50000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                if (locationResult.locations.isNotEmpty()) {
                    val location = locationResult.lastLocation
                    if (location != null) {
                        Log.i("lat",location.latitude.toString())
                        Log.i("long",location.longitude.toString())

                            weatherViewModel.listWeathersTransaction(location.latitude.toLong(),
                            location.longitude.toLong(),getlistCalendars())

                        //   val latLng = LatLng(location.latitude, location.longitude)
                    }
                }
            }
        }
    }

    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null
        )
    }


    private fun getlistCalendars() : List<Long>{

        val listdates = ArrayList<Long>()
        var calendary = Calendar.getInstance()
        calendary.add(Calendar.HOUR,-1)// Fist Date
        listdates.add(calendary.timeInMillis / 1000)  //Save FistDate
        Log.i("Date One",calendary.time.toString())
        calendary.add(Calendar.DAY_OF_WEEK,-1) // Second Date
        listdates.add(calendary.timeInMillis / 1000) //Save SeconDate
        Log.i("Date One",calendary.time.toString())
        calendary.add(Calendar.DAY_OF_WEEK,-2) // Second Date
        listdates.add(calendary.timeInMillis / 1000 )  //Save thirthDate
        Log.i("Date One",calendary.time.toString())
        calendary.add(Calendar.DAY_OF_WEEK,-3) // Four Date
        listdates.add(calendary.timeInMillis / 1000 )  //Save FourDate
        Log.i("Date One",calendary.time.toString())
        calendary.add(Calendar.DAY_OF_WEEK,-4) // Five Date
        listdates.add(calendary.timeInMillis/ 1000 )  //Save Five Date
        Log.i("Date One",calendary.time.toString())
        return listdates
    }








    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}