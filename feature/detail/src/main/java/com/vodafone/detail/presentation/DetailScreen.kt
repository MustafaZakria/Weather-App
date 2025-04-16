package com.vodafone.detail.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kaffeeapp.components.ErrorImage
import com.vodafone.detail.presentation.component.DailyItem
import com.vodafone.detail.presentation.component.DetailTopBar
import com.vodafone.detail.presentation.component.WeatherInfoCard

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    onNavigateToHome: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState) {
        Log.d("DetailScreen", "uiState: $uiState")
    }

    DetailScreenContent(
        uiState = uiState,
        onBackClick = onNavigateToHome
    )
}

@Composable
fun DetailScreenContent(
    uiState: DetailUiState,
    onBackClick: () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(
                top = dimensionResource(com.vodafone.core.R.dimen.screen_top_padding),
                start = dimensionResource(com.vodafone.core.R.dimen.padding_md),
                end = dimensionResource(com.vodafone.core.R.dimen.padding_md),
                bottom = dimensionResource(com.vodafone.core.R.dimen.padding_md)
            ),
        topBar = {
            DetailTopBar(
                onBackClick = onBackClick,
                cityName = uiState.detail?.cityName ?: ""
            )
        },
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(com.vodafone.core.R.dimen.padding_md)),
            contentPadding = PaddingValues(vertical = dimensionResource(com.vodafone.core.R.dimen.padding_sm))
        ) {
            uiState.detail?.let { weatherDetail ->
                item {
                    WeatherInfoCard(
                        weatherDetail = weatherDetail,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                items(weatherDetail.dailyForecast) { daily ->
                    DailyItem(daily)
                }

            }
            item {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(top = dimensionResource(id = com.vodafone.core.R.dimen.padding_lg))
                    )
                }

                if (uiState.isError) {
                    ErrorImage(
                        modifier = Modifier.padding(top = dimensionResource(id = com.vodafone.core.R.dimen.padding_lg))
                    )
                }
            }
        }
    }
}