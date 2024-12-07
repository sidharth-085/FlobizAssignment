package com.example.flobizassignment.presentation.utils

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.flobizassignment.domain.models.Transaction
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
            "id" to transaction.id,
            "date" to transaction.date,
            "description" to transaction.description,
            "amount" to transaction.amount,
            "type" to transaction.type,
        )
    }

    fun onError(e: Exception) {
        e.printStackTrace()
        Log.d("Hello Tagg", e.message.toString())
    }
}