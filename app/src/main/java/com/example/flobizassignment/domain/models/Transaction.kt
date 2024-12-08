package com.example.flobizassignment.domain.models

data class Transaction(
    val transactionId: String = "",
    val date: String = "",
    val description: String = "",
    val amount: Double = 0.0,
    val type: String = ""
) {
    // No-argument constructor is provided implicitly due to default values
}