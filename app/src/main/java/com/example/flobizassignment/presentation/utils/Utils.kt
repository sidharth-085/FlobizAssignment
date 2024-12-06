package com.example.flobizassignment.presentation.utils

import android.os.Build
import androidx.annotation.RequiresApi
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
}