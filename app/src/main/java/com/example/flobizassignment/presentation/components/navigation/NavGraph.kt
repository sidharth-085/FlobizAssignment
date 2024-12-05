package com.example.flobizassignment.presentation.components.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.flobizassignment.presentation.screens.dashboard.DashboardScreen

@Composable
fun NavGraph(startDestination: String) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Routes.DashboardScreen.routes) {
            DashboardScreen()
        }
    }
}