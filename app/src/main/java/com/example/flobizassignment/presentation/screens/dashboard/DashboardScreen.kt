package com.example.flobizassignment.presentation.screens.dashboard

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
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
import com.example.flobizassignment.domain.models.Transaction
import com.example.flobizassignment.presentation.components.AddNewButton
import com.example.flobizassignment.presentation.components.SwipeToDeleteContainer
import com.example.flobizassignment.presentation.components.TransactionCard
import com.example.flobizassignment.presentation.components.topbar.SearchTopBar
import com.example.flobizassignment.presentation.theme.FlobizAssignmentTheme
import com.example.flobizassignment.presentation.theme.background
import com.example.flobizassignment.presentation.theme.textColorSecondary

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DashboardScreen(
    navigateToViewEditTransactionScreen: (transaction: Transaction) -> Unit = {},
    onAddNewButtonClick: () -> Unit,
    dashboardViewModel: DashboardViewModel = hiltViewModel(),
) {
    var isSearching by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val transactions by dashboardViewModel.transactions.collectAsState()
    val isLoading by dashboardViewModel.isLoading.collectAsState()
    val filteredTransactions = transactions.filter {
        it.description.contains(searchQuery, ignoreCase = true)
    }

    LaunchedEffect(Unit) {
        dashboardViewModel.getTransactions()
    }

    BackHandler(enabled = isSearching) {
        isSearching = false
        searchQuery = ""
    }

    FlobizAssignmentTheme {
        Scaffold(
            topBar = {
                SearchTopBar(
                    searchQuery = searchQuery,
                    onIsSearchingChange = { isSearching = it },
                    onSearchQueryChange = { searchQuery = it }
                )
            },
            floatingActionButton = { AddNewButton(
                onClick = onAddNewButtonClick
            ) },
            content = { padding ->
                DashboardContent(
                    navigateToViewEditTransactionScreen = navigateToViewEditTransactionScreen,
                    isLoading = isLoading,
                    updatedTransactions = filteredTransactions
                )
            },
            floatingActionButtonPosition = FabPosition.Center
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DashboardContent(
    navigateToViewEditTransactionScreen: (transaction: Transaction) -> Unit,
    isLoading: Boolean,
    updatedTransactions: List<Transaction>
) {
    Column(
        modifier = Modifier
            .padding(top = 66.dp)
            .fillMaxSize()
            .background(background)
    ) {
        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            Text(
                text = "Recent Transactions",
                color = textColorSecondary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Box(Modifier.fillMaxSize()) {
                if (isLoading) {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
                else if (updatedTransactions.isEmpty()) {
                    Text(
                        text = "No Transactions",
                        color = Color.Gray,
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = updatedTransactions,
                            key = { it.transactionId }
                        ) { transaction ->
                            SwipeToDeleteContainer(
                                item = transaction,
                                onDelete = { }
                            ) {
                                TransactionCard(transaction = transaction) { transaction ->
                                    navigateToViewEditTransactionScreen(transaction)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}