package com.example.flobizassignment.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.flobizassignment.domain.models.Expense
import com.example.flobizassignment.domain.repository.FireStoreRepository
import com.example.flobizassignment.presentation.utils.Utils
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
class FireStoreRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
): FireStoreRepository {
    override suspend fun addExpense(expense: Expense): Boolean {
        return try {
            firestore.collection("expenses")
                .add(Utils.map(expense))
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }
}