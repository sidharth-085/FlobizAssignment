package com.example.flobizassignment.presentation.components.navigation

sealed class Routes(val routes: String) {
    data object LoginScreen : Routes("login_screen")

    data object DashboardScreen : Routes("dashboard_screen")

    data object SettingsScreen : Routes("settings_screen")
}