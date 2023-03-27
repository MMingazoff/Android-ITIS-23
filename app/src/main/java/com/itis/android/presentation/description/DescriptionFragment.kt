package com.itis.android.presentation.description

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.itis.android.R
import com.itis.android.databinding.FragmentDescriptionBinding
import com.itis.android.di.App
import com.itis.android.domain.weather.DetailedWeatherInfo
import com.itis.android.domain.weather.GetDetailedWeatherByCityIdUseCase
import com.itis.android.utils.showSnackbar
import javax.inject.Inject

class DescriptionFragment : Fragment(R.layout.fragment_description) {

    private var binding: FragmentDescriptionBinding? = null

    @Inject
    lateinit var getDetailedWeatherByCityIdUseCase: GetDetailedWeatherByCityIdUseCase

    private val descriptionViewModel: DescriptionViewModel by viewModels {
        DescriptionViewModel.provideFactory(getDetailedWeatherByCityIdUseCase)
    }

    override fun onAttach(context: Context) {
        App.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDescriptionBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        descriptionViewModel.setCityId(arguments?.getInt(CITY_ID))
    }

    private fun observeViewModel() {
        with(descriptionViewModel) {
            weatherInfo.observe(viewLifecycleOwner) {
                if (it == null) return@observe
                setWeatherInfo(it)
            }
            error.observe(viewLifecycleOwner) {
                if (it)
                    binding?.root?.showSnackbar(R.string.unknown_error)
            }
        }
    }

    private fun setWeatherInfo(weather: DetailedWeatherInfo) {
        binding?.apply {
            tvCityName.text = weather.name
            tvTemp.text = requireContext().getString(R.string.temp, weather.temp)
            tvDesc.text = weather.description
            tvTempRange.text = requireContext().getString(
                R.string.temp_range,
                weather.tempMax,
                weather.tempMin
            )
            tvHumidity.text = requireContext().getString(
                R.string.humidity,
                weather.humidity
            )
            tvWind.text = requireContext().getString(
                R.string.wind,
                weather.windSpeed,
                getWindDirection(weather.windDegrees)
            )
            tvPressure.text = requireContext().getString(
                R.string.pressure,
                weather.pressure
            )
            tvTempFeels.text = requireContext().getString(
                R.string.temp_feels,
                weather.tempFeelsLike
            )
            ivTempIcon.load(
                "https://openweathermap.org/img/w/${weather.icon}.png"
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
