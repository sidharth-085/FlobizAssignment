package com.example.flobizassignment.domain.models

data class Expense(
    val id: String,
    val date: String,
    val description: String,
    val amount: Double,
    val type: String,
)