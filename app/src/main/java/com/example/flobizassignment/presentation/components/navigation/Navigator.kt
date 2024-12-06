package com.example.flobizassignment.presentation.components.navigation

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.credentials.GetCredentialRequest
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.flobizassignment.R
import com.example.flobizassignment.domain.models.BottomNavItem
import com.example.flobizassignment.domain.models.Expense
import com.example.flobizassignment.presentation.screens.dashboard.DashboardScreen
import com.example.flobizassignment.presentation.screens.expense.AddExpenseScreen
import com.example.flobizassignment.presentation.screens.expense.ViewEditExpenseScreen
import com.example.flobizassignment.presentation.screens.login.LoginScreen
import com.example.flobizassignment.presentation.screens.settings.SettingsScreen
import com.example.flobizassignment.presentation.theme.background
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigator(
    onLogOutClick: () -> Unit
) {
    val bottomNavigationItems = remember {
        listOf(
            BottomNavItem(icon = R.drawable.ic_home, name = "Home"),
            BottomNavItem(icon = R.drawable.ic_settings, name = "Settings"),
        )
    }

    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableStateOf(0)
    }

    selectedItem = when (backStackState?.destination?.route) {
        Routes.DashboardScreen.route -> 0
        else -> 1
    }

    val isBottomBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == Routes.DashboardScreen.route ||
                backStackState?.destination?.route == Routes.SettingsScreen.route
    }

    val startDestination = rememberSaveable {
        mutableStateOf(
            if (Firebase.auth.currentUser != null) {
                Routes.DashboardScreen.route
            } else {
                Routes.LoginScreen.route
            }
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize().background(background),
        bottomBar = {
            if (isBottomBarVisible) {
                BottomNavigation(
                    items = bottomNavigationItems,
                    selectedItem = selectedItem,
                    onItemClick = { index ->
                        when (index) {
                            0 -> navigateToTab(
                                navController = navController,
                                route = Routes.DashboardScreen.route
                            )
                            1 -> navigateToTab(
                                navController = navController,
                                route = Routes.SettingsScreen.route
                            )
                        }
                    }
                )
            }
        }
    ) { val bottomPadding = it.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = Routes.DashboardScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {
            composable(
                route = Routes.DashboardScreen.route
            ) {
                DashboardScreen(
                    navigateToViewEditExpenseScreen = { expense ->
                        navigateToViewEditExpenseScreen(
                            navController = navController,
                            expense = expense
                        )
                    },
                )
            }

            composable(
                route = Routes.SettingsScreen.route
            ) {
                SettingsScreen(
                    onLogOutClick = onLogOutClick
                )
            }

            composable(
                route = Routes.AddExpenseScreen.route
            ) {
                AddExpenseScreen(
                    navController = navController,
                    auth = Firebase.auth
                )
            }

            composable(
                route = Routes.ViewEditExpenseScreen.route,
                arguments = listOf(navArgument("expenses") { type = NavType.StringType })
            ) { backStackEntry ->
                val expense = navController.previousBackStackEntry?.savedStateHandle?.get<Expense?>("expense")
                expense?.let {
                    ViewEditExpenseScreen(
                        expense = expense,
                        navController = navController,
                        firebaseAuth = Firebase.auth
                    )
                }
            }
        }
    }
}

private fun navigateToTab(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { screenRoute ->
            popUpTo(screenRoute) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}

private fun navigateToViewEditExpenseScreen(navController: NavController, expense: Expense) {
    navController.currentBackStackEntry?.savedStateHandle?.set("expense", expense)
    navController.navigate(
        Routes.ViewEditExpenseScreen.route
    )
}