package com.example.flobizassignment.presentation.components.navigation

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
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.flobizassignment.R
import com.example.flobizassignment.domain.models.BottomNavModel
import com.example.flobizassignment.presentation.theme.background

@Composable
fun AppNavigator() {
    val bottomNavigationItems = remember {
        listOf(
            BottomNavModel(icon = R.drawable.ic_home, name = "Home"),
            BottomNavModel(icon = R.drawable.ic_settings, name = "Settings"),
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

    Scaffold(
        modifier = Modifier.fillMaxSize().background(background),
        bottomBar = {
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
    ) { val bottomPadding = it.calculateBottomPadding()
        NavGraph(
            startDestination = Routes.DashboardScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        )
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