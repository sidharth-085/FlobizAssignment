package com.example.flobizassignment.domain.usecases

import com.example.flobizassignment.domain.repository.TransactionRepository
import javax.inject.Inject

class DeleteTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    suspend operator fun invoke(
        transactionId: String,
        onSuccessCallback: () -> Unit,
        onFailureCallback: (e: Exception) -> Unit
    ) {
        transactionRepository.deleteTransaction(
            transactionId,
            onSuccessCallback,
            onFailureCallback
        )
    }
}