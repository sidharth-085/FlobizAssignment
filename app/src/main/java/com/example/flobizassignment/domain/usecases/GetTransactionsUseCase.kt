package com.example.flobizassignment.domain.usecases

import com.example.flobizassignment.domain.models.Transaction
import com.example.flobizassignment.domain.repository.TransactionRepository
import javax.inject.Inject

class GetTransactionsUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    suspend operator fun invoke(): List<Transaction> {
        return transactionRepository.getTransactions()
    }
}