package com.example.flobizassignment.presentation.screens.expense.viewmodel

import androidx.lifecycle.ViewModel
import com.example.flobizassignment.domain.models.Expense
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ViewExpenseViewModel @Inject constructor(
    private val firestore: FirebaseFirestore
): ViewModel() {
    private val _isLoading = MutableStateFlow(value = false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun updateExpense(
        expenseId: String,
        updatedExpense: Expense,
        onSuccessCallback: () -> Unit,
        onFailureCallback: (e: Exception) -> Unit
    ) {
        _isLoading.value = true

        firestore.collection("expenses")
            .document(expenseId)
            .set(updatedExpense)
            .addOnSuccessListener {
                _isLoading.value = false
                onSuccessCallback()
            }
            .addOnFailureListener { error ->
                _isLoading.value = false
                onFailureCallback(error)
            }
    }

    fun deleteExpense(
        expenseId: String,
        onSuccessCallback: () -> Unit,
        onFailureCallback: (e: Exception) -> Unit
    ) {
        _isLoading.value = true

        firestore.collection("expenses")
            .document(expenseId)
            .delete()
            .addOnSuccessListener {
                _isLoading.value = false
                onSuccessCallback()
            }
            .addOnFailureListener { error ->
                _isLoading.value = false
                onFailureCallback(error)
            }
    }
}