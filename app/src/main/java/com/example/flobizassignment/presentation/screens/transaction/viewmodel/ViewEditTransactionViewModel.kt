package com.example.flobizassignment.presentation.screens.transaction.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flobizassignment.domain.models.Transaction
import com.example.flobizassignment.domain.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewEditTransactionViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
): ViewModel() {

    private val _isLoading = MutableStateFlow(value = false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun updateTransaction(
        updatedTransaction: Transaction,
        onSuccessCallback: () -> Unit,
        onFailureCallback: (e: Exception) -> Unit
    ) {
        _isLoading.value = true

        viewModelScope.launch {
            transactionRepository.updateTransaction(
                transaction = updatedTransaction,
                onSuccessCallback = {
                    _isLoading.value = false
                    onSuccessCallback()
                },
                onFailureCallback = {
                    _isLoading.value = false
                    onFailureCallback(it)
                }
            )
        }
    }

    fun deleteTransaction(
        transaction: Transaction,
        onSuccessCallback: () -> Unit,
        onFailureCallback: (e: Exception) -> Unit
    ) {
        _isLoading.value = true

        viewModelScope.launch {
            transactionRepository.deleteTransaction(
                transactionId = transaction.transactionId,
                onSuccessCallback = {
                    _isLoading.value = false
                    onSuccessCallback()
                },
                onFailureCallback = {
                    _isLoading.value = false
                    onFailureCallback(it)
                }
            )
        }
    }
}