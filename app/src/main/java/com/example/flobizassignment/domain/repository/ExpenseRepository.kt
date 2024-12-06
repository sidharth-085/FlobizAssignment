package com.example.flobizassignment.domain.repository

import com.example.flobizassignment.domain.models.Expense
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ExpenseRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {

    suspend fun getUserExpenses(): List<Expense> {
        val userId = auth.currentUser?.uid ?: throw IllegalStateException("User not logged in")
        val snapshot = firestore.collection("expenses").whereEqualTo("uid",userId).get().await()
        return snapshot.documents.map { document ->
            document.toObject(Expense::class.java)?.copy(id = document.id) ?: Expense(
                id = "",
                date = "",
                amount = 0.0,
                description = "",
                type = ""
            )
        }
    }
}