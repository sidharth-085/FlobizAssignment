package com.example.flobizassignment.domain.usecases

import com.example.flobizassignment.domain.models.Expense
import com.example.flobizassignment.domain.repository.FireStoreRepository
import javax.inject.Inject

class AddExpenseUseCase @Inject constructor(
    private val fireStoreRepository: FireStoreRepository
) {
    suspend operator fun invoke(expense: Expense): Boolean {
        return fireStoreRepository.addExpense(expense)
    }
}