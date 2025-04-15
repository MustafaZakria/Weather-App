package com.vodafone.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vodafone.home.presentation.HomeScreen


const val HOME_SCREEN_ROUTE = "home"

fun NavGraphBuilder.homeScreen() {
    composable(
        route = HOME_SCREEN_ROUTE
    ) {
        HomeScreen(
            onNavigateToDetail = {},
            onNavigateToSearch = {}
        )
    }
}