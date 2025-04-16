package com.vodafone.detail.presentation

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodafone.core.domain.model.WeatherDetail
import com.vodafone.detail.domain.GetWeatherDetailUseCase
import com.vodafone.detail.navigation.LAT_ARG
import com.vodafone.detail.navigation.LON_ARG
import com.zek.cryptotracker.core.domain.util.onError
import com.zek.cryptotracker.core.domain.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getWeatherDetailUseCase: GetWeatherDetailUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    init {
        val lat = savedStateHandle.get<String>(LAT_ARG)?.toDoubleOrNull()
        val lon = savedStateHandle.get<String>(LON_ARG)?.toDoubleOrNull()

        if (lat != null && lon != null) {
            getWeatherDetail(lat, lon)
        } else {
            _uiState.update { it.copy(isError = true) }
        }
    }

    private fun getWeatherDetail(lat: Double, lon: Double) = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }

        getWeatherDetailUseCase(lat, lon)
            .onSuccess { detail ->
                _uiState.update { it.copy(detail = detail) }
            }
            .onError { error ->
                Log.d("DetailViewModel", "${error.toString()}")
                _uiState.update { it.copy(isError = true) }
            }

        _uiState.update { it.copy(isLoading = false) }
    }
}

data class DetailUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val detail: WeatherDetail? = null
)
