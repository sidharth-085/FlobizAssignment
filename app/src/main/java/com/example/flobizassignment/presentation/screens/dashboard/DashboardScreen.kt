package com.example.flobizassignment.presentation.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flobizassignment.presentation.components.AddNewButton
import com.example.flobizassignment.presentation.components.TransactionCard
import com.example.flobizassignment.presentation.components.topbar.SearchTopBar
import com.example.flobizassignment.presentation.theme.FlobizAssignmentTheme
import com.example.flobizassignment.presentation.theme.background
import com.example.flobizassignment.presentation.theme.textColorSecondary

@Composable
fun DashboardScreen() {
    FlobizAssignmentTheme {
        Scaffold(
            topBar = { SearchTopBar() },
            floatingActionButton = { AddNewButton() },
            content = { padding ->
                DashboardContent()
            },
            floatingActionButtonPosition = FabPosition.Center
        )
    }
}

@Composable
fun DashboardContent() {
    Column(
        modifier = Modifier
            .padding(top = 60.dp)
            .fillMaxSize()
            .background(background)
    ) {
        Column {
            Text(
                text = "Recent Transactions",
                color = textColorSecondary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 20.dp
                )
            )

            Spacer(modifier = Modifier.height(15.dp))

            LazyColumn {
                items(count = 100) {
                    TransactionCard()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview(){
    FlobizAssignmentTheme {
        DashboardScreen()
    }
}