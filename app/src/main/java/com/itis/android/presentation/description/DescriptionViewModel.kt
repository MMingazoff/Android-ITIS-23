package com.itis.android.presentation.description

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.itis.android.di.DataContainer
import com.itis.android.domain.DetailedWeatherInfo
import com.itis.android.domain.GetDetailedWeatherByCityIdUseCase
import kotlinx.coroutines.launch

class DescriptionViewModel(
    private val getDetailedWeatherByCityIdUseCase: GetDetailedWeatherByCityIdUseCase
) : ViewModel() {

    private val _weatherInfo = MutableLiveData<DetailedWeatherInfo?>(null)
    val weatherInfo: LiveData<DetailedWeatherInfo?>
        get() = _weatherInfo

    private val _error = MutableLiveData(false)
    val error: LiveData<Boolean>
        get() = _error

    fun setCityId(cityId: Int?) {
        if (cityId != null)
            getWeatherInfo(cityId)
    }

    private fun getWeatherInfo(cityId: Int) {
        viewModelScope.launch {
            try {
                _weatherInfo.value =
                    getDetailedWeatherByCityIdUseCase(cityId)
                _error.value = false
            } catch (e: Exception) {
                _error.value = true
            }
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                DescriptionViewModel(DataContainer.getDetailedWeatherByCityIdUseCase)
            }
        }
    }
}
