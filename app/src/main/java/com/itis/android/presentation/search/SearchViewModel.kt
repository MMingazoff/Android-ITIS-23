package com.itis.android.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.itis.android.di.DataContainer
import com.itis.android.domain.CityWeatherInfo
import com.itis.android.domain.GetNearestCitiesWeatherInfoUseCase
import com.itis.android.domain.GetWeatherByCityNameUseCase
import com.itis.android.domain.WeatherInfo
import kotlinx.coroutines.launch

class SearchViewModel(
    private val getWeatherByCityNameUseCase: GetWeatherByCityNameUseCase,
    private val getNearestCitiesWeatherInfoUseCase: GetNearestCitiesWeatherInfoUseCase,
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

    fun searchNearestCities(latitude: Double, longitude: Double) {
        getNearestCitiesWeatherInfo(latitude, longitude)
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

    private fun getNearestCitiesWeatherInfo(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            try {
                _loading.value = true
                _nearestCitiesWeatherInfo.value =
                    getNearestCitiesWeatherInfoUseCase(latitude, longitude, 10)
                _error.value = false
            } catch (e: Exception) {
                _error.value = true
            } finally {
                _loading.value = false
            }
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                SearchViewModel(
                    DataContainer.getWeatherByCityNameUseCase,
                    DataContainer.getNearestCitiesWeatherInfoUseCase,
                )
            }
        }
    }
}
