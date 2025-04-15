package com.vodafone.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vodafone.core.domain.model.Weather
import com.vodafone.core.presentation.ui.theme.WeatherappTheme
import com.vodafone.home.R
import com.vodafone.home.presentation.component.HomeTopBar
import com.vodafone.home.presentation.component.WeatherCard

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
    onNavigateToDetail: (Weather) -> Unit,
    onNavigateToSearch: () -> Unit
) {
    val recentCityState by homeViewModel.uiState.collectAsState()
    val error by homeViewModel.errorFlow.collectAsState(initial = null)

    HomeScreenContent(
        onSearchClick = onNavigateToSearch,
        onWeatherItemClick = onNavigateToDetail,
        recentCityState = recentCityState
    )
}

@Composable
fun HomeScreenContent(
    onSearchClick: () -> Unit = {},
    onWeatherItemClick: (Weather) -> Unit = {},
    recentCityState: RecentCityState,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 40.dp, start = 16.dp, end = 16.dp),
        topBar = {
            HomeTopBar(onSearchClick = onSearchClick)
        },
    ) { paddingValues ->

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
        ) {
            when (recentCityState) {

                RecentCityState.Loading -> {
                    Box(
                        modifier = Modifier
                            .height(170.dp)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                RecentCityState.NoRecentCity -> {
                    Box(
                        modifier = Modifier
                            .height(170.dp)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.no_recent_weather),
                            fontSize = dimensionResource(com.vodafone.core.R.dimen.text_md).value.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Medium,
                        )
                    }
                }

                is RecentCityState.Success -> {
                    WeatherCard(
                        weather = recentCityState.weather,
                        modifier = Modifier
                            .height(170.dp)
                            .fillMaxWidth()
                            .background(Color.Transparent)
                            .padding(dimensionResource(com.vodafone.core.R.dimen.padding_md))
                            .clickable {
                                onWeatherItemClick(recentCityState.weather)
                            }
                    )
                }

                RecentCityState.Error -> {}
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    WeatherappTheme {
        HomeScreenContent(
            recentCityState = RecentCityState.Success(
                Weather(
                    city = "Cairo",
                    icon = "https://openweathermap.org/img/wn/03n@2x.png",
                    condition = "cloudy",
                    temperature = "28C",
                    date = "12/12/222",
                )
            )
        )
    }
}
