package com.example.flobizassignment.presentation.screens.expense

sealed class ExpenseState {
    data object Initial : ExpenseState()
    data object Loading : ExpenseState()
    data object Completed : ExpenseState()
    data object Failed : ExpenseState()
}