package com.example.flobizassignment.presentation.components.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.flobizassignment.R
import com.example.flobizassignment.domain.models.BottomNavItem
import com.example.flobizassignment.domain.models.Transaction
import com.example.flobizassignment.presentation.screens.dashboard.DashboardScreen
import com.example.flobizassignment.presentation.screens.transaction.AddTransactionScreen
import com.example.flobizassignment.presentation.screens.transaction.ViewEditTransactionScreen
import com.example.flobizassignment.presentation.screens.login.LoginScreen
import com.example.flobizassignment.presentation.screens.login.LoginViewModel
import com.example.flobizassignment.presentation.screens.settings.SettingsScreen
import com.example.flobizassignment.presentation.theme.background
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    loginViewModel: LoginViewModel = hiltViewModel(),
    onSignInClick: () -> Unit,
    onLogOutClick: () -> Unit,
) {
    val navigationViewModel = viewModel<NavigationViewModel>()

    val bottomNavigationItems = remember {
        listOf(
            BottomNavItem(icon = R.drawable.ic_home, name = "Dashboard", route = Routes.DashboardScreen.route),
            BottomNavItem(icon = R.drawable.ic_settings, name = "Settings", route = Routes.SettingsScreen.route),
        )
    }

    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value

    val startDestination by rememberSaveable {
        mutableStateOf(
            navigationViewModel.getStartDestination()
        )
    }

    val selectedItem by rememberSaveable {
        mutableIntStateOf(value = when (backStackState?.destination?.route) {
            Routes.DashboardScreen.route -> 0
            else -> 1
        })
    }

    val isBottomBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == Routes.DashboardScreen.route ||
                backStackState?.destination?.route == Routes.SettingsScreen.route
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
                    },
                    backStackEntry = backStackState
                )
            }
        }
    ) { val bottomPadding = it.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {
            composable(route = Routes.LoginScreen.route) {
                val signInState by loginViewModel.signInState.collectAsStateWithLifecycle()

                LaunchedEffect(key1 = Unit) {
                    if (navigationViewModel.getLoginStatus()) {
                        navController.navigate(
                            route = Routes.DashboardScreen.route
                        )
                    }
                }

                LaunchedEffect(key1 = signInState.isSignInSuccessful) {
                    if (signInState.isSignInSuccessful) {
                        navController.navigate(
                            route = Routes.DashboardScreen.route
                        )

                        loginViewModel.resetState()
                    }
                }

                LoginScreen(
                    signInState = signInState,
                    onSignInClick = onSignInClick
                )
            }

            composable(
                route = Routes.DashboardScreen.route
            ) {
                DashboardScreen(
                    onAddNewButtonClick = {
                        navController.navigate(
                            route = Routes.AddTransactionScreen.route
                        )
                    },
                    navigateToViewEditTransactionScreen = { transaction ->
                        navigateToViewEditTransactionScreen(
                            navController = navController,
                            transaction = transaction
                        )
                    }
                )
            }

            composable(
                route = Routes.SettingsScreen.route
            ) {
                SettingsScreen(
                    onLogOutClick = {
                        onLogOutClick()
                        navController.navigate(route = Routes.LoginScreen.route) {
                            popUpTo(Routes.DashboardScreen.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(
                route = Routes.AddTransactionScreen.route
            ) {
                AddTransactionScreen(
                    navController = navController,
                    auth = Firebase.auth
                )
            }

            composable(
                route = Routes.ViewEditTransactionScreen.route,
                arguments = listOf(navArgument("transactions") { type = NavType.StringType })
            ) { backStackEntry ->
                val transaction = navController.previousBackStackEntry?.savedStateHandle?.get<Transaction?>("transaction")
                transaction?.let {
                    ViewEditTransactionScreen(
                        transaction = transaction,
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

private fun navigateToViewEditTransactionScreen(navController: NavController, transaction: Transaction) {
    navController.currentBackStackEntry?.savedStateHandle?.set("transaction", transaction)
    navController.navigate(
        Routes.ViewEditTransactionScreen.route
    )
}