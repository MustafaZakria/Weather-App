package com.vodafone.weather_app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.vodafone.detail.navigation.detailScreen
import com.vodafone.detail.navigation.navigateToDetail
import com.vodafone.home.navigation.HOME_SCREEN_ROUTE
import com.vodafone.home.navigation.homeScreen
import com.vodafone.home.navigation.navigateToHome
import com.vodafone.search.navigation.navigateToSearch
import com.vodafone.search.navigation.searchScreen

@Composable
fun MainNavGraph(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController(),
    startDestination: String = HOME_SCREEN_ROUTE
) {

    NavHost(navController = navHostController, startDestination = startDestination) {
        homeScreen(
            onNavigateToDetail = { lat, lon ->
                navHostController.navigateToDetail(lat, lon)
            },
            onNavigateToSearch = {
                navHostController.navigateToSearch()
            }
        )
        detailScreen(
            onNavigateToHome = {
                navHostController.navigateToHome()
            }
        )
        searchScreen(
            onNavigateToDetail = { lat, lon ->
                navHostController.navigateToDetail(lat, lon)
            },
            onNavigateToHome = {
                navHostController.navigateToHome()
            }
        )
    }
}