package com.vodafone.search.navigation

import androidx.navigation.compose.composable

import androidx.navigation.NavGraphBuilder
import com.vodafone.search.SearchScreen

private const val SEARCH_SCREEN_ROUTE = "search"

fun NavGraphBuilder.searchScreen() {
    composable(
        route = SEARCH_SCREEN_ROUTE
    ) {
        SearchScreen()
    }
}