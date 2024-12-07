package com.example.flobizassignment.presentation.components.navigation

sealed class Routes(val route: String) {

    data object LoginScreen : Routes("login_screen")

    data object DashboardScreen : Routes("dashboard_screen")

    data object SettingsScreen : Routes("settings_screen")

    data object ViewEditTransactionScreen : Routes("transaction_detail_screen/{transactions}") {
        fun createRoute(transactions: String): String = "transaction_detail_screen/$transactions"
    }

    data object AddTransactionScreen: Routes("add_transaction_screen")
}