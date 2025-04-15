package com.vodafone.detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.vodafone.detail.presentation.DetailScreen

private const val DETAIL_SCREEN_ROUTE = "detail"
const val latArg = "lat"
const val lonArg = "lat"

fun NavController.navigateToDetail(lat: String, lon: String) {
    navigate("$DETAIL_SCREEN_ROUTE/$lat/$lon")
}

fun NavGraphBuilder.detailScreen(
    onNavigateToHome: () -> Unit
) {
    composable(
        route = "$DETAIL_SCREEN_ROUTE/{$latArg}/{$lonArg}",
        arguments = listOf(
            navArgument(latArg) { type = NavType.StringType },
            navArgument(lonArg) { type = NavType.StringType }
        )
    ) {
        DetailScreen(
            onNavigateToHome = onNavigateToHome
        )
    }
}