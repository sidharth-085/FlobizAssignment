package com.example.flobizassignment.presentation.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.flobizassignment.presentation.components.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {
    private val _startDestination = mutableStateOf(Routes.AppStartNavigation.route)
    val startDestination: State<String> = _startDestination


}