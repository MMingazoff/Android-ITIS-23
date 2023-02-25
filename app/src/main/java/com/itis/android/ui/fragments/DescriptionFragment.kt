package com.itis.android.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import coil.load
import com.itis.android.R
import com.itis.android.data.Network
import com.itis.android.data.response.WeatherResponse
import com.itis.android.databinding.FragmentDescriptionBinding
import com.itis.android.utils.showSnackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

class DescriptionFragment : Fragment(R.layout.fragment_description) {

    private var binding: FragmentDescriptionBinding? = null
    private var cityId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cityId = it.getInt(CITY_ID)
        }
        binding = FragmentDescriptionBinding.inflate(layoutInflater)
        initWeatherUi()
    }

    private fun initWeatherUi() {
        lifecycleScope.launch {
            try {
                val weather = cityId?.let { id ->
                    Network.weatherApi.getWeather(id)
                }
                withContext(Dispatchers.Main) {
                    if (weather != null)
                        setWeatherInfo(weather)
                }
            } catch (e: UnknownHostException) {
                binding?.root?.showSnackbar(R.string.unknown_error)
            }
        }
    }

    private fun setWeatherInfo(weather: WeatherResponse) {
        binding?.apply {
            tvCityName.text = weather.name
            tvTemp.text = requireContext().getString(R.string.temp, weather.main.temp)
            tvDesc.text = weather.weather.first().description
            tvTempRange.text = requireContext().getString(
                R.string.temp_range,
                weather.main.tempMax,
                weather.main.tempMin
            )
            tvHumidity.text = requireContext().getString(
                R.string.humidity,
                weather.main.humidity
            )
            tvWind.text = requireContext().getString(
                R.string.wind,
                weather.wind.speed,
                getWindDirection(weather.wind.deg)
            )
            tvPressure.text = requireContext().getString(
                R.string.pressure,
                weather.main.pressure
            )
            tvTempFeels.text = requireContext().getString(
                R.string.temp_feels,
                weather.main.feelsLike
            )
            ivTempIcon.load(
                "https://openweathermap.org/img/w/${weather.weather.first().icon}.png"
            )
        }
    }

    private fun getWindDirection(degrees: Int): String =
        when (degrees) {
            in 1..22 -> "N"
            in 23..68 -> "NE"
            in 69..114 -> "E"
            in 115..160 -> "SE"
            in 161..206 -> "S"
            in 207..252 -> "SW"
            in 253..298 -> "W"
            in 299..344 -> "NW"
            else -> "N"
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding?.root
    }

    companion object {
        private const val CITY_ID = "CITY_ID"

        fun newInstance(cityId: Int) =
            DescriptionFragment().apply {
                arguments = Bundle().apply {
                    putInt(CITY_ID, cityId)
                }
            }
    }
}
