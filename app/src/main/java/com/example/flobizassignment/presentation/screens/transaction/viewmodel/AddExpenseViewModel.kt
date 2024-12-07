package com.example.flobizassignment.presentation.screens.transaction.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flobizassignment.domain.models.Transaction
import com.example.flobizassignment.domain.usecases.AddTransactionUseCase
import com.example.flobizassignment.presentation.screens.transaction.TransactionState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val addTransactionUseCase: AddTransactionUseCase,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _stateFlow = MutableStateFlow<TransactionState>(TransactionState.Initial)
    val stateFlow: StateFlow<TransactionState> = _stateFlow

    fun saveTransaction(transaction: Transaction) {
        val currentUserId = firebaseAuth.currentUser?.uid

        currentUserId?.let {
            viewModelScope.launch {
                _stateFlow.value = TransactionState.Loading
                val isSaved = addTransactionUseCase.invoke(transaction)
                _stateFlow.value = if (isSaved) TransactionState.Completed else TransactionState.Failed
            }
        } ?: run {
            _stateFlow.value = TransactionState.Failed
        }
    }
}