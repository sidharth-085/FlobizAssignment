package com.example.flobizassignment.presentation.screens.transaction

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.flobizassignment.domain.models.Transaction
import com.example.flobizassignment.presentation.components.topbar.ViewEditTransactionTopBar
import com.example.flobizassignment.presentation.screens.transaction.viewmodel.ViewEditTransactionViewModel
import com.example.flobizassignment.presentation.theme.background
import com.example.flobizassignment.presentation.theme.colorSecondary
import com.example.flobizassignment.presentation.theme.textColorSecondary
import com.google.firebase.auth.FirebaseAuth

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ViewEditTransactionScreen(
    transaction: Transaction,
    navController: NavController,
    firebaseAuth: FirebaseAuth,
    viewTransactionViewModel: ViewEditTransactionViewModel = hiltViewModel(),
) {
    var edit by remember { mutableStateOf(false) }
    val type by remember { mutableStateOf(transaction.type) }
    val date by remember { mutableStateOf(transaction.date) }
    var description by remember { mutableStateOf(transaction.description) }
    var amount by remember { mutableStateOf(transaction.amount.toString()) }
    val isLoading by viewTransactionViewModel.isLoading.collectAsState()
    val userId = firebaseAuth.currentUser?.uid.toString()

    Scaffold(
        topBar = {
            ViewEditTransactionTopBar(
                transaction = Transaction(
                    transactionId = transaction.transactionId,
                    type = type,
                    description = description,
                    amount = amount.toDouble(),
                    date = date
                ),
                navController = navController,
                edit = edit,
                changeEditMode = { value ->
                    edit = value
                }
            )
        },
        content = { padding->
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            else {
                Column(
                    modifier = Modifier.padding(padding)
                        .fillMaxSize()
                        .background(background)
                ) {

                    Spacer(modifier = Modifier.padding(top = 10.dp))

                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .background(Color.White)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = type,
                                fontSize = 12.sp,
                                color = colorSecondary,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = date,
                                fontSize = 12.sp,
                                color = textColorSecondary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.padding(bottom = 10.dp))

                    Box(
                        modifier = Modifier.fillMaxWidth().background(Color.White)
                    ) {
                        Column {
                            Text(
                                text = "Description",
                                fontSize = 13.sp,
                                color = Color.DarkGray,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(
                                    start = 16.dp,
                                    top = 12.dp,
                                    bottom = 10.dp
                                )
                            )

                            Divider(modifier = Modifier.fillMaxWidth().height(1.dp))

                            if (edit) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 10.dp, vertical = 10.dp)
                                        .background(
                                            color = Color.White,
                                            shape = RoundedCornerShape(10.dp)
                                        )
                                        .border(
                                            width = 1.dp,
                                            color = colorSecondary,
                                            shape = RoundedCornerShape(10.dp)
                                        )
                                ) {
                                    TextField(
                                        value = description,
                                        onValueChange = { description = it },
                                        modifier = Modifier.fillMaxWidth(),
                                        colors = TextFieldDefaults.colors().copy(
                                            focusedContainerColor = Color.White,
                                            focusedTextColor = textColorSecondary,
                                            unfocusedTextColor = textColorSecondary,
                                            focusedIndicatorColor = Color.Transparent,
                                            unfocusedContainerColor = Color.White,
                                            unfocusedIndicatorColor = Color.White
                                        )
                                    )
                                }
                            }
                            else {
                                Text(
                                    text = description,
                                    fontSize = 17.sp,
                                    color = textColorSecondary,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(
                                        start = 16.dp,
                                        bottom = 12.dp,
                                        top = 10.dp
                                    )
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.padding(bottom = 10.dp))

                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .background(Color.White)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp).fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Total Amount",
                                fontSize = 15.sp,
                                color = Color.DarkGray,
                                fontWeight = FontWeight.Bold
                            )

                            if (edit) {
                                TextField(
                                    value = amount,
                                    onValueChange = {change ->
                                        if (change.all { it.isDigit() }) {
                                            amount = change
                                        }
                                    },
                                    modifier = Modifier.width(100.dp)
                                        .background(
                                            color = Color.White,
                                            shape = RoundedCornerShape(10.dp)
                                        )
                                        .border(
                                            width = 1.dp,
                                            color = colorSecondary,
                                            shape = RoundedCornerShape(10.dp)
                                        ),
                                    colors = TextFieldDefaults.colors().copy(
                                        focusedContainerColor = Color.White,
                                        focusedTextColor = textColorSecondary,
                                        unfocusedTextColor = textColorSecondary,
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedContainerColor = Color.White,
                                        unfocusedIndicatorColor = Color.White
                                    ),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                                )
                            } else {
                                Text(
                                    text = "₹ $amount",
                                    fontSize = 15.sp,
                                    color = Color.DarkGray,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}