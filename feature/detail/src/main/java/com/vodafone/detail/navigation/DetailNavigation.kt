package com.vodafone.detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.vodafone.detail.presentation.DetailScreen

private const val DETAIL_SCREEN_ROUTE = "detail"
const val LAT_ARG = "lat"
const val LON_ARG = "lon"

fun NavController.navigateToDetail(lat: String, lon: String) {
    navigate("$DETAIL_SCREEN_ROUTE/$lat/$lon")
}

fun NavGraphBuilder.detailScreen(
    onNavigateToHome: () -> Unit
) {
    composable(
        route = "$DETAIL_SCREEN_ROUTE/{$LAT_ARG}/{$LON_ARG}",
        arguments = listOf(
            navArgument(LAT_ARG) { type = NavType.StringType },
            navArgument(LON_ARG) { type = NavType.StringType }
        )
    ) {
        DetailScreen(
            onNavigateToHome = onNavigateToHome
        )
    }
}