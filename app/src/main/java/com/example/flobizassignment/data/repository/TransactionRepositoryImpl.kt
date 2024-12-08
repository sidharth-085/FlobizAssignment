package com.example.flobizassignment.data.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.flobizassignment.domain.models.Transaction
import com.example.flobizassignment.domain.repository.TransactionRepository
import com.example.flobizassignment.presentation.utils.Utils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
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
            val transactionRef = firestore.collection("users").document(uid)
                .collection("transactions").document()

            Log.d("Hello Tagg", "hello ${transaction.transactionId}")
            Log.d("Hello Tagg", "hello ${transactionRef.id}")

            val transactionWithId = transaction.copy(transactionId = transactionRef.id)

            transactionRef.set(Utils.map(transactionWithId)).await()
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
                    Log.d("Hello Tagg", "getTransactions ${document.id}")
                    document.toObject(Transaction::class.java)?.copy(transactionId = document.id) ?: Transaction()
                }
        } ?: emptyList()
    }

    override suspend fun updateTransaction(
        transaction: Transaction,
        onSuccessCallback: () -> Unit,
        onFailureCallback: (e: Exception) -> Unit
    ) {
        val userId = firebaseAuth.currentUser?.uid

        userId?.let { uid ->
            firestore.collection("users").document(uid)
                .collection("transactions").document(transaction.transactionId)
                .set(Utils.map(transaction), SetOptions.merge())
                .addOnSuccessListener {
                    onSuccessCallback()
                }
                .addOnFailureListener { exception ->
                    onFailureCallback(exception)
                }.await()
        }
    }

    override suspend fun deleteTransaction(
        transactionId: String,
        onSuccessCallback: () -> Unit,
        onFailureCallback: (e: Exception) -> Unit
    ) {
       Log.d("Hello Tagg", "deleteTransaction $transactionId")
        val userId = firebaseAuth.currentUser?.uid

        userId?.let { uid ->
            firestore.collection("users").document(uid)
                .collection("transactions").document(transactionId)
                .delete()
                .addOnSuccessListener {
                    onSuccessCallback()
                }
                .addOnFailureListener { exception ->
                    onFailureCallback(exception)
                }.await()
        }
    }
}