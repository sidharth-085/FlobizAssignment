package com.example.flobizassignment.presentation.components.navigation

sealed class Routes(val route: String) {

    data object LoginScreen : Routes("login_screen")

    data object DashboardScreen : Routes("dashboard_screen")

    data object SettingsScreen : Routes("settings_screen")

    data object ViewEditExpenseScreen : Routes("expense_detail_screen/{expenses}") {
        fun createRoute(expenses: String): String = "expense_detail_screen/$expenses"
    }

    data object AddExpenseScreen: Routes("add_expense_screen")
}