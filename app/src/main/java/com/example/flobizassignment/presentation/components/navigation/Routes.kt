package com.example.flobizassignment.presentation.components.navigation

sealed class Routes(val route: String) {
    data object AppStartNavigation : Routes("app_start_navigation")

    data object LoginScreen : Routes("login_screen")

    data object DashboardScreen : Routes("dashboard_screen")

    data object SettingsScreen : Routes("settings_screen")

    data object AppNavigation: Routes("app_navigation")

    data object AppNavigatorScreen: Routes("app_navigator_screen")
}