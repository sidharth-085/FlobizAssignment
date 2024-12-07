package com.example.flobizassignment.presentation.screens.transaction

sealed class TransactionState {
    data object Initial : TransactionState()
    data object Loading : TransactionState()
    data object Completed : TransactionState()
    data object Failed : TransactionState()
}