package com.example.flobizassignment.presentation.screens.login

import androidx.lifecycle.ViewModel
import com.example.flobizassignment.domain.models.SignInResult
import com.example.flobizassignment.domain.models.SignInState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(): ViewModel() {
    private val _signInState: MutableStateFlow<SignInState> = MutableStateFlow(SignInState())
    val signInState: StateFlow<SignInState> = _signInState.asStateFlow()

    fun updateSignInState(result: SignInResult) {
        _signInState.update {
            it.copy(
                isSignInSuccessful = result.isSuccessful != null,
                signInError = result.errorMessage
            )
        }
    }

    fun resetState() {
        _signInState.update { SignInState() }
    }
}