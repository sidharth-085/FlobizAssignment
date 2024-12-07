package com.example.flobizassignment.presentation.screens.expense.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flobizassignment.domain.models.Expense
import com.example.flobizassignment.domain.usecases.AddExpenseUseCase
import com.example.flobizassignment.presentation.screens.expense.ExpenseState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val addExpenseUseCase: AddExpenseUseCase,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _stateFlow = MutableStateFlow<ExpenseState>(ExpenseState.Initial)
    val stateFlow: StateFlow<ExpenseState> = _stateFlow

    fun saveExpense(expense: Expense) {
        val currentUserId = firebaseAuth.currentUser?.uid

        currentUserId?.let {
            viewModelScope.launch {
                _stateFlow.value = ExpenseState.Loading
                val isSaved = addExpenseUseCase.invoke(expense)
                _stateFlow.value = if (isSaved) ExpenseState.Completed else ExpenseState.Failed
            }
        } ?: run {
            _stateFlow.value = ExpenseState.Failed
        }
    }
}