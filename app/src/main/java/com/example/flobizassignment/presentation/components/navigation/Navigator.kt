package com.example.flobizassignment.presentation.components.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.flobizassignment.R
import com.example.flobizassignment.domain.models.BottomNavModel

@Composable
fun Navigator() {
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
        Routes.DashboardScreen.routes -> 0
        else -> 1
    }
}