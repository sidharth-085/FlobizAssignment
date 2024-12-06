package com.example.flobizassignment.presentation.components.navigation

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.flobizassignment.presentation.screens.login.LoginScreen
import com.example.flobizassignment.presentation.screens.login.LoginViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    startDestination: String,
    onSignInClick: () -> Unit,
    onLogOutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        navigation(
            route = Routes.StartNavigation.route,
            startDestination = Routes.LoginScreen.route
        ) {
            composable(route = Routes.LoginScreen.route){
                val loginViewModel = viewModel<LoginViewModel>()
                val signInState by loginViewModel.signInState.collectAsStateWithLifecycle()

                LaunchedEffect(key1 = signInState) {
                    Toast.makeText(
                        context,
                        "Sign In Successful",
                        Toast.LENGTH_LONG
                    ).show()

                    navController.navigate(route = Routes.AppNavigation.route)
                }

                LoginScreen(
                    signInState = signInState,
                    onSignInClick = onSignInClick
                )
            }
        }

        navigation(
            route = Routes.AppNavigation.route,
            startDestination = Routes.AppNavigatorScreen.route
        ) {
            composable(route = Routes.AppNavigatorScreen.route){
                AppNavigator(onLogOutClick = onLogOutClick)
            }
        }
    }
}