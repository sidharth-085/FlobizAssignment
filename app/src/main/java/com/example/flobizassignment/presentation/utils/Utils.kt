package com.example.flobizassignment.presentation.utils

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.flobizassignment.domain.models.Transaction
import com.google.gson.Gson
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
object Utils {

    fun convertMillisToDate(millis: Long): LocalDate {
        val instant = Instant.ofEpochMilli(millis)
        val zoneDateTime = instant.atZone(ZoneId.systemDefault())
        return zoneDateTime.toLocalDate()
    }

    fun formatDateToString(localDate: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())
        return formatter.format(localDate)
    }

    fun map(transaction: Transaction): HashMap<String, Any> {
        return hashMapOf(
            "id" to transaction.transactionId,
            "date" to transaction.date,
            "description" to transaction.description,
            "amount" to transaction.amount,
            "type" to transaction.type,
        )
    }

    fun onError(e: Exception) {
        e.printStackTrace()
    }

    private fun convertToJsonString(transaction: Transaction): String {
        return Gson().toJson(transaction)
    }

    fun createRouteForTransaction(transaction: Transaction): String {
        val transactionJson = convertToJsonString(transaction)
        return "view_edit_transaction_screen/$transactionJson"
    }
}