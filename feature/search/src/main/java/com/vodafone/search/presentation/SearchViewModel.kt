package com.vodafone.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodafone.core.domain.model.City
import com.vodafone.search.domain.GetCitiesByQueryUseCase
import com.vodafone.search.domain.UpdateRecentCityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getCitiesByQueryUseCase: GetCitiesByQueryUseCase,
    private val updateRecentCityUseCase: UpdateRecentCityUseCase
) : ViewModel() {

    private val _cityList = MutableStateFlow<List<City>>(listOf())
    val cityList = _cityList.asStateFlow()

    private val _cityListLoading = MutableStateFlow(true)
    val cityListLoading = _cityListLoading.asStateFlow()

    private val _searchValue = MutableStateFlow("")
    val searchValue = _searchValue.asStateFlow()

    init {
        viewModelScope.launch {
            _searchValue
                .debounce(300)
                .distinctUntilChanged()
                .collect { value ->
                    getCitiesByQuery(value)
                }
        }
    }

    private suspend fun getCitiesByQuery(query: String) {
        _cityListLoading.update { true }

        val cities = getCitiesByQueryUseCase.invoke(query)

        _cityList.update { cities }
        _cityListLoading.update { false }
    }

    fun onSelectCity(city: City) {
        updateRecentCityUseCase.invoke(city)
    }

    fun onSearchValueChange(value: String) {
        _searchValue.value = value
    }
}