package com.vodafone.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodafone.core.domain.model.Weather
import com.vodafone.data.repository.CityRepository
import com.vodafone.home.domain.GetRecentCityUseCase
import com.vodafone.home.domain.util.RecentCityError.NO_RECENT_CITY
import com.zek.cryptotracker.core.domain.util.onError
import com.zek.cryptotracker.core.domain.util.onSuccess
import com.zek.remotedatasource.core.presentation.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRecentCityUseCase: GetRecentCityUseCase,
    private val cityRepository: CityRepository
) : ViewModel() {

    private val _cityState = MutableStateFlow<RecentCityState>(RecentCityState.Loading)
    val cityState = _cityState.asStateFlow()

    private var errorChannel = Channel<UiText>(Channel.BUFFERED)
    var errorFlow = errorChannel.receiveAsFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            cityRepository.loadCities()
            loadRecentCity()
        }
    }

    private suspend fun loadRecentCity() {
        getRecentCityUseCase()
            .onSuccess { weather ->
                _cityState.update { RecentCityState.Success(weather) }
            }
            .onError { error ->
                if (error == NO_RECENT_CITY) {  //because it has a different handling
                    _cityState.update { RecentCityState.NoRecentCity }
                } else {
                    _cityState.update { RecentCityState.Error(error.asUiText()) }
                    errorChannel.send(error.asUiText())
                }
            }
    }
}

sealed class RecentCityState {
    class Success(val weather: Weather) : RecentCityState()
    class Error(val error: UiText) : RecentCityState()
    data object Loading : RecentCityState()
    data object NoRecentCity : RecentCityState()
}