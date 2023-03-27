package com.itis.android.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.itis.android.di.App
import com.itis.android.di.DataContainer
import com.itis.android.domain.geolocation.GeoLocation
import com.itis.android.domain.geolocation.GetCurrentLocationUseCase
import com.itis.android.domain.geolocation.GetLastLocationUseCase
import com.itis.android.domain.weather.CityWeatherInfo
import com.itis.android.domain.weather.GetNearestCitiesWeatherInfoUseCase
import com.itis.android.domain.weather.GetWeatherByCityNameUseCase
import com.itis.android.domain.weather.WeatherInfo
import kotlinx.coroutines.launch

class SearchViewModel(
    private val getWeatherByCityNameUseCase: GetWeatherByCityNameUseCase,
    private val getNearestCitiesWeatherInfoUseCase: GetNearestCitiesWeatherInfoUseCase,
    private val getLastLocationUseCase: GetLastLocationUseCase,
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase
) : ViewModel() {

    private val _weatherInfo = MutableLiveData<WeatherInfo?>(null)
    val weatherInfo: LiveData<WeatherInfo?>
        get() = _weatherInfo

    private val _error = MutableLiveData(false)
    val error: LiveData<Boolean>
        get() = _error

    private val _nearestCitiesWeatherInfo = MutableLiveData<List<CityWeatherInfo>?>()
    val nearestCitiesWeatherInfo: LiveData<List<CityWeatherInfo>?>
        get() = _nearestCitiesWeatherInfo

    private val _loading = MutableLiveData(true)
    val loading: LiveData<Boolean>
        get() = _loading

    fun searchWeather(query: String) {
        getWeatherInfo(query)
    }

    fun searchNearestCities(permsGiven: Boolean) {
        viewModelScope.launch {
            getNearestCitiesWeatherInfo(
                if (permsGiven) {
                    getLocation()
                } else {
                    GeoLocation(DEFAULT_LON, DEFAULT_LAT)
                }
            )
        }
    }

    private suspend fun getLocation() =
        getLastLocationUseCase().let {
            it ?: getCurrentLocationUseCase()
        }

    private fun getWeatherInfo(query: String) {
        viewModelScope.launch {
            try {
                _loading.value = true
                _weatherInfo.value = getWeatherByCityNameUseCase(query)
                _weatherInfo.value = null
                _error.value = false
            } catch (e: Exception) {
                _error.value = true
            } finally {
                _loading.value = false
            }
        }
    }

    private suspend fun getNearestCitiesWeatherInfo(geoLocation: GeoLocation) {
        try {
            _loading.value = true
            _nearestCitiesWeatherInfo.value =
                getNearestCitiesWeatherInfoUseCase(geoLocation, 10)
            _error.value = false
        } catch (e: Exception) {
            _error.value = true
        } finally {
            _loading.value = false
        }
    }

    companion object {
        // Moscow coordinates
        private const val DEFAULT_LAT = 55.75
        private const val DEFAULT_LON = 37.62

        val Factory = viewModelFactory {
            initializer {
                SearchViewModel(
                    DataContainer.getWeatherByCityNameUseCase,
                    DataContainer.getNearestCitiesWeatherInfoUseCase,
                    App.getLastLocationUseCase,
                    App.getCurrentLocationUseCase
                )
            }
        }
    }
}
