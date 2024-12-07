package com.example.flobizassignment.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.flobizassignment.domain.models.Transaction
import com.example.flobizassignment.domain.repository.TransactionRepository
import com.example.flobizassignment.presentation.utils.Utils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
class TransactionRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
): TransactionRepository {

    override suspend fun addTransaction(transaction: Transaction): Boolean {
        val userId = firebaseAuth.currentUser?.uid

        return userId?.let { uid ->
            firestore.collection("users").document(uid)
                .collection("transactions")
                .add(Utils.map(transaction))
                .await()
            true
        } ?: false
    }

    override suspend fun getTransactions(): List<Transaction> {
        val userId = firebaseAuth.currentUser?.uid

        return userId?.let { uid ->
            firestore.collection("users").document(uid)
                .collection("transactions")
                .orderBy("date", Query.Direction.DESCENDING)
                .get().await()
                .documents.map { document ->
                    document.toObject(Transaction::class.java)?.copy(id = document.id) ?: Transaction()
                }
        } ?: emptyList()
    }
}