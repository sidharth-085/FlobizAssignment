package com.example.flobizassignment.presentation.components.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController

@Composable
fun NavGraph(
    startDestination: String,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        navigation(
            route = Routes.AppStartNavigation.route,
            startDestination = Routes.LoginScreen.route
        ) {
            composable(route = Routes.LoginScreen.route){
                // TODO
            }
        }

        navigation(
            route = Routes.AppNavigation.route,
            startDestination = Routes.AppNavigatorScreen.route
        ) {
            composable(route = Routes.AppNavigatorScreen.route){
                AppNavigator()
            }
        }
    }
}