package com.example.flobizassignment.domain.models

sealed class TransactionState {
    data object Initial : TransactionState()
    data object Loading : TransactionState()
    data object Completed : TransactionState()
    data object Failed : TransactionState()
}