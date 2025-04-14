package com.vodafone.weather_app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.vodafone.detail.navigation.detailScreen
import com.vodafone.home.navigation.HOME_SCREEN_ROUTE
import com.vodafone.home.navigation.homeScreen
import com.vodafone.search.navigation.searchScreen

@Composable
fun MainNavGraph(
    navHostController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
    startDestination: String = HOME_SCREEN_ROUTE
) {

    NavHost(navController = navHostController, startDestination = startDestination) {
        homeScreen()
        detailScreen()
        searchScreen()
    }
}