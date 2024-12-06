package com.example.flobizassignment.presentation.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flobizassignment.domain.usecases.GetLoginStatusUseCase
import com.example.flobizassignment.presentation.components.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getLoginStatusUseCase: GetLoginStatusUseCase
): ViewModel() {
    private val _startDestination = mutableStateOf(Routes.StartNavigation.route)
    val startDestination: State<String> = _startDestination

    init {
        getLoginStatusUseCase().onEach { isUserLoggedIn ->
            when (isUserLoggedIn) {
                true -> _startDestination.value = Routes.AppNavigation.route
                false -> _startDestination.value = Routes.StartNavigation.route
            }
        }.launchIn(viewModelScope)
    }
}