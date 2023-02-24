package com.itis.android.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.itis.android.R
import com.itis.android.data.Network
import com.itis.android.databinding.FragmentSearchBinding
import com.itis.android.ui.recyclerview.SpaceItemDecoration
import com.itis.android.ui.recyclerview.WeatherAdapter
import com.itis.android.ui.recyclerview.WeatherUi
import com.itis.android.utils.showSnackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class SearchFragment : Fragment(R.layout.fragment_search) {

    private var binding: FragmentSearchBinding? = null
    private var fusedLocationClient: FusedLocationProviderClient? = null

    @SuppressLint("MissingPermission")
    private val requestLocationPermissions =
        registerForActivityResult(RequestMultiplePermissions()) { perms ->
            val coarseLocationGranted = perms[Manifest.permission.ACCESS_COARSE_LOCATION]
            val fineLocationGranted = perms[Manifest.permission.ACCESS_FINE_LOCATION]
            if (coarseLocationGranted == true && fineLocationGranted == true) {
                fusedLocationClient?.lastLocation?.addOnSuccessListener {
                    getAndSetWeather(it.latitude, it.longitude)
                }
            } else {
                getAndSetWeather(DEFAULT_LAT, DEFAULT_LON)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSearchBinding.inflate(layoutInflater)
        binding?.rvCities?.addItemDecoration(
            SpaceItemDecoration(requireContext(), 16f)
        )
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        setCitySearch()
        requestLocationPermissions.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun setCitySearch() {
        binding?.searchView?.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null)
                    getWeather(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean = false

        })
    }

    private fun getWeather(city: String) {
        lifecycleScope.launch {
            try {
                val result = Network.weatherApi.getWeather(city)
                withContext(Dispatchers.Main) {
                    parentFragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .setCustomAnimations(
                            android.R.anim.slide_in_left,
                            android.R.anim.slide_out_right,
                            android.R.anim.slide_in_left,
                            android.R.anim.slide_out_right
                        )
                        .replace(R.id.container, DescriptionFragment.newInstance(result.id))
                        .commit()
                }
            } catch (e: HttpException) {
                binding?.root?.showSnackbar(R.string.city_not_found)
            }
        }
    }

    private fun getAndSetWeather(latitude: Double, longitude: Double) {
        lifecycleScope.launch {
            val cities = getCitiesWithWeather(latitude, longitude)
            withContext(Dispatchers.Main) {
                setCitiesWithWeather(cities)
                binding?.progressBar?.visibility = View.GONE
            }
        }
    }

    private suspend fun getCitiesWithWeather(latitude: Double, longitude: Double): List<WeatherUi> {
        val cities = Network.weatherApi.getNearestCitiesWeather(latitude, longitude).cities
        val citiesWithWeather = mutableListOf<WeatherUi>()
        cities.forEach {
            citiesWithWeather.add(
                WeatherUi(
                    id = it.id,
                    city = it.name,
                    temp = it.main.temp,
                    icon = it.weather.first().icon
                )
            )
        }
        return citiesWithWeather
    }

    private fun setCitiesWithWeather(cities: List<WeatherUi>) {
        binding?.rvCities?.adapter = WeatherAdapter(
            cities,
            requireContext(),
        ) {
            parentFragmentManager.beginTransaction()
                .addToBackStack(null)
                .setCustomAnimations(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right,
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right
                )
                .replace(R.id.container, DescriptionFragment.newInstance(it.id))
                .commit()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding?.root
    }

    companion object {
        private const val DEFAULT_LAT = 55.74
        private const val DEFAULT_LON = 49.18
    }
}
