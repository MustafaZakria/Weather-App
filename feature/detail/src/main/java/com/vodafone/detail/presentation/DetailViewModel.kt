package com.vodafone.detail.presentation

import androidx.lifecycle.ViewModel
import com.vodafone.detail.domain.GetWeatherDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getWeatherDetailUseCase: GetWeatherDetailUseCase
): ViewModel() {

}
