package com.example.flobizassignment.presentation.screens.transaction.viewmodel

import androidx.lifecycle.ViewModel
import com.example.flobizassignment.domain.models.Transaction
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ViewEditTransactionViewModel @Inject constructor(
    private val firestore: FirebaseFirestore
): ViewModel() {
    private val _isLoading = MutableStateFlow(value = false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun updateTransaction(
        transactionId: String,
        updatedTransaction: Transaction,
        onSuccessCallback: () -> Unit,
        onFailureCallback: (e: Exception) -> Unit
    ) {
        _isLoading.value = true

        firestore.collection("transactions")
            .document(transactionId)
            .set(updatedTransaction)
            .addOnSuccessListener {
                _isLoading.value = false
                onSuccessCallback()
            }
            .addOnFailureListener { error ->
                _isLoading.value = false
                onFailureCallback(error)
            }
    }

    fun deleteTransaction(
        transactionId: String,
        onSuccessCallback: () -> Unit,
        onFailureCallback: (e: Exception) -> Unit
    ) {
        _isLoading.value = true

        firestore.collection("transactions")
            .document(transactionId)
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