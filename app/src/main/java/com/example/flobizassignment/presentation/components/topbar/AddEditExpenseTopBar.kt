package com.example.flobizassignment.presentation.components.topbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flobizassignment.R

@Composable
fun AddEditExpenseTopBar() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp)
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(30.dp),
                painter = painterResource(R.drawable.ic_arrow_left),
                contentDescription = "Back Button"
            )

            Spacer(modifier = Modifier.width(15.dp))

            Text(
                text = "Record Expense",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}