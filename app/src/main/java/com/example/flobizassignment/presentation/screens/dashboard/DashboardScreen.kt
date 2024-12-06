package com.example.flobizassignment.presentation.screens.dashboard

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.flobizassignment.domain.models.Expense
import com.example.flobizassignment.presentation.components.AddNewButton
import com.example.flobizassignment.presentation.components.TransactionCard
import com.example.flobizassignment.presentation.components.topbar.SearchTopBar
import com.example.flobizassignment.presentation.theme.FlobizAssignmentTheme
import com.example.flobizassignment.presentation.theme.background
import com.example.flobizassignment.presentation.theme.textColorSecondary

@Composable
fun DashboardScreen(
    navigateToViewEditExpenseScreen: (expense: Expense) -> Unit = {},
    dashboardViewModel: DashboardViewModel = hiltViewModel(),
) {
    var isSearching by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val expenses by dashboardViewModel.expenses.collectAsState()
    val isLoading by dashboardViewModel.isLoading.collectAsState()
    val filteredExpenses = expenses.filter {
        it.description.contains(searchQuery, ignoreCase = true)
    }

    LaunchedEffect(Unit) {
        dashboardViewModel.fetchExpenses()
    }

    BackHandler(enabled = isSearching) {
        isSearching = false
        searchQuery = ""
    }

    FlobizAssignmentTheme {
        Scaffold(
            topBar = {
                SearchTopBar(
                    isSearching = isSearching,
                    searchQuery = searchQuery,
                    onIsSearchingChange = { isSearching = it },
                    onSearchQueryChange = { searchQuery = it }
                )
            },
            floatingActionButton = { AddNewButton() },
            content = { padding ->
                DashboardContent(
                    navigateToViewEditExpenseScreen = navigateToViewEditExpenseScreen,
                    isLoading = isLoading,
                    updatedExpenses = filteredExpenses
                )
            },
            floatingActionButtonPosition = FabPosition.Center
        )
    }
}

@Composable
fun DashboardContent(
    navigateToViewEditExpenseScreen: (expense: Expense) -> Unit,
    isLoading: Boolean,
    updatedExpenses: List<Expense>
) {
    Column(
        modifier = Modifier
            .padding(top = 90.dp)
            .fillMaxSize()
            .background(background)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Recent Transactions",
                color = textColorSecondary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Box(Modifier.fillMaxSize()) {
                if (isLoading) {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
                else if (updatedExpenses.isEmpty()) {
                    Text(
                        text = "No Transactions",
                        color = Color.Gray,
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(updatedExpenses) { expense ->
                            TransactionCard(expense = expense) { e ->
                                navigateToViewEditExpenseScreen(e)
                            }
                        }
                    }
                }
            }
        }
    }
}