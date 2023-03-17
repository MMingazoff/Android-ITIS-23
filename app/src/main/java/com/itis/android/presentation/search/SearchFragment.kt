package com.itis.android.presentation.search

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.itis.android.R
import com.itis.android.databinding.FragmentSearchBinding
import com.itis.android.presentation.description.DescriptionFragment
import com.itis.android.presentation.search.recyclerview.SpaceItemDecoration
import com.itis.android.presentation.search.recyclerview.WeatherAdapter
import com.itis.android.utils.showSnackbar

class SearchFragment : Fragment(R.layout.fragment_search) {

    private var binding: FragmentSearchBinding? = null

    private var fusedLocationClient: FusedLocationProviderClient? = null

    private var adapter: WeatherAdapter? = null

    private val searchViewModel: SearchViewModel by viewModels {
        SearchViewModel.Factory
    }

    @SuppressLint("MissingPermission")
    private val requestLocationPermissions =
        registerForActivityResult(RequestMultiplePermissions()) { perms ->
            val coarseLocationGranted = perms[Manifest.permission.ACCESS_COARSE_LOCATION]
            val fineLocationGranted = perms[Manifest.permission.ACCESS_FINE_LOCATION]
            if (coarseLocationGranted == true || fineLocationGranted == true) {
                fusedLocationClient?.lastLocation?.addOnSuccessListener { location ->
                    searchNearestCities(location)
                }
            } else {
                searchViewModel.searchNearestCities(DEFAULT_LAT, DEFAULT_LON)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCitySearch()
        getLocation()
        setCitiesWeatherAdapter()
        observeViewModel()
    }

    private fun observeViewModel() {
        with(searchViewModel) {
            weatherInfo.observe(viewLifecycleOwner) {
                if (it == null) return@observe
                navigateToDetailedWeather(it.id)
            }
            error.observe(viewLifecycleOwner) {
                if (it)
                    binding?.root?.showSnackbar(R.string.unknown_error)
            }
            loading.observe(viewLifecycleOwner) {
                binding?.progressBar?.isVisible = it
            }
            nearestCitiesWeatherInfo.observe(viewLifecycleOwner) {
                if (it == null) return@observe
                adapter?.submitList(it)
            }
        }
    }

    private fun getLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        requestLocationPermissions.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    @SuppressLint("MissingPermission")
    private fun searchNearestCities(location: Location?) {
        if (location == null) {
            fusedLocationClient?.getCurrentLocation(
                Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                null
            )?.addOnSuccessListener {
                searchViewModel.searchNearestCities(it.latitude, it.longitude)
            }
            return
        }
        searchViewModel.searchNearestCities(location.latitude, location.longitude)
    }

    private fun setCitySearch() {
        binding?.searchView?.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null)
                    searchViewModel.searchWeather(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean = false

        })
    }

    private fun setCitiesWeatherAdapter() {
        binding?.rvCities?.apply {
            addItemDecoration(SpaceItemDecoration(requireContext(), 16f))
            adapter = WeatherAdapter(requireContext()) {
                navigateToDetailedWeather(it.id)
            }.also {
                this@SearchFragment.adapter = it
            }
        }
    }

    private fun navigateToDetailedWeather(id: Int) {
        parentFragmentManager.beginTransaction()
            .addToBackStack(null)
            .setCustomAnimations(
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right,
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right
            )
            .replace(R.id.container, DescriptionFragment.newInstance(id))
            .commit()
    }

    companion object {
        // Moscow coordinates
        private const val DEFAULT_LAT = 55.75
        private const val DEFAULT_LON = 37.62
    }
}
