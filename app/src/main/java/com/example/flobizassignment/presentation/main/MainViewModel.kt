package com.example.flobizassignment.presentation.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.flobizassignment.presentation.components.navigation.Routes

class MainViewModel: ViewModel() {
    private val _startDestination = mutableStateOf(Routes.AppStartNavigation.route)
    val startDestination: State<String> = _startDestination
}