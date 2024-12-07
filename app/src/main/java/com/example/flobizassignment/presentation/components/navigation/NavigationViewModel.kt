package com.example.flobizassignment.presentation.components.navigation

import androidx.lifecycle.ViewModel
import com.example.flobizassignment.domain.usecases.GetLoginStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val getUserLoginStatusUseCase: GetLoginStatusUseCase
) : ViewModel() {

    fun getStartDestination(): String {
        return if (getUserLoginStatusUseCase.invoke()) Routes.DashboardScreen.route
        else Routes.LoginScreen.route
    }

    fun getLoginStatus(): Boolean {
        return getUserLoginStatusUseCase()
    }
}