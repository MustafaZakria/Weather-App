package com.vodafone.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vodafone.search.SearchScreen

private const val SEARCH_SCREEN_ROUTE = "search"

fun NavController.navigateToSearch() {
    navigate(SEARCH_SCREEN_ROUTE)
}

fun NavGraphBuilder.searchScreen(
    onNavigateToDetail: (lat: String, lon: String) -> Unit,
    onNavigateToHome: () -> Unit
) {
    composable(
        route = SEARCH_SCREEN_ROUTE
    ) {
        SearchScreen(
            onNavigateToDetail = { city ->
                onNavigateToDetail(
                    city.lat.toString(),
                    city.lon.toString()
                )
            },
            onNavigateToHome = onNavigateToHome
        )
    }
}