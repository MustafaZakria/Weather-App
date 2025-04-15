package com.vodafone.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.vodafone.home.presentation.HomeScreen


const val HOME_SCREEN_ROUTE = "home"

fun NavHostController.navigateToHome() {
    navigate(HOME_SCREEN_ROUTE)
}

fun NavGraphBuilder.homeScreen(
    onNavigateToDetail: (lat: String, lon: String) -> Unit,
    onNavigateToSearch: () -> Unit
) {
    composable(
        route = HOME_SCREEN_ROUTE
    ) {
        HomeScreen(
            onNavigateToDetail = { weather ->
                onNavigateToDetail(
                    weather.lat.toString(),
                    weather.lon.toString()
                )
            },
            onNavigateToSearch = onNavigateToSearch
        )
    }
}