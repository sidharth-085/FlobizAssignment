package com.example.flobizassignment.presentation.components.navigation

sealed class Routes(val route: String) {

    data object LoginScreen : Routes("login_screen")

    data object DashboardScreen : Routes("dashboard_screen")

    data object SettingsScreen : Routes("settings_screen")

    data object ViewEditTransactionScreen : Routes("view_edit_transaction_screen/{transactions}")

    data object AddTransactionScreen: Routes("add_transaction_screen")
}