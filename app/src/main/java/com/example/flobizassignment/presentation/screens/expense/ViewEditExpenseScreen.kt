package com.example.flobizassignment.presentation.screens.expense

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.flobizassignment.domain.models.Expense
import com.example.flobizassignment.presentation.components.topbar.ViewEditExpenseTopBar
import com.example.flobizassignment.presentation.screens.expense.viewmodel.ViewEditExpenseViewModel
import com.example.flobizassignment.presentation.theme.background
import com.example.flobizassignment.presentation.theme.colorPrimaryVariant
import com.example.flobizassignment.presentation.theme.colorSecondary
import com.example.flobizassignment.presentation.theme.textColorSecondary
import com.google.firebase.auth.FirebaseAuth

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ViewEditExpenseScreen(
    expense: Expense,
    navController: NavController,
    firebaseAuth: FirebaseAuth,
    viewExpenseViewModel: ViewEditExpenseViewModel = hiltViewModel(),
) {
    var edit by remember { mutableStateOf(false) }
    val type by remember { mutableStateOf(expense.type) }
    val date by remember { mutableStateOf(expense.date) }
    var description by remember { mutableStateOf(expense.description) }
    var amount by remember { mutableStateOf(expense.amount.toString()) }
    val isLoading by viewExpenseViewModel.isLoading.collectAsState()
    val userId = firebaseAuth.currentUser?.uid.toString()

    Scaffold(
        topBar = {
            ViewEditExpenseTopBar(
                expense = Expense(
                    id = userId,
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
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Text(
                                text = type,
                                fontSize = 15.sp,
                                color = colorSecondary,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.padding(top = 15.dp))

                            Text(
                                text = date,
                                fontSize = 15.sp,
                                color = textColorSecondary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.padding(bottom = 10.dp))

                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .background(Color.White)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Text(
                                text = "Description",
                                fontSize = 16.sp,
                                color = textColorSecondary,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.padding(top = 20.dp))

                            if (edit) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            color = Color.White,
                                            shape = RoundedCornerShape(10.dp)
                                        )
                                        .border(
                                            width = 1.dp,
                                            color = colorSecondary,
                                            shape = RoundedCornerShape(10.dp)
                                        )
                                        .padding(4.dp)
                                ) {
                                    TextField(
                                        value = description,
                                        onValueChange = { description = it },
                                        label = { Text("Description") },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .border(
                                                width = 1.dp,
                                                color = colorSecondary,
                                                shape = RoundedCornerShape(10.dp)
                                            )
                                            .clip(RoundedCornerShape(10.dp)),
                                        colors = TextFieldDefaults.colors().copy(
                                            focusedContainerColor = colorPrimaryVariant,
                                            focusedTextColor = textColorSecondary,
                                            unfocusedTextColor = textColorSecondary,
                                            focusedIndicatorColor = Color.Transparent,
                                            unfocusedContainerColor = colorPrimaryVariant,
                                            unfocusedIndicatorColor = Color.Transparent
                                        )
                                    )
                                }
                            } else {
                                Text(
                                    text = description,
                                    fontSize = 15.sp,
                                    color = textColorSecondary,
                                    fontWeight = FontWeight.Bold
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
                            modifier = Modifier.padding(20.dp).fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Total Amount",
                                fontSize = 18.sp,
                                color = textColorSecondary,
                                fontWeight = FontWeight.Bold
                            )

                            if (edit) {
                                TextField(
                                    value = amount,
                                    onValueChange = { amount = it },
                                    label = { Text("Amount") },
                                    modifier = Modifier.width(100.dp).background(
                                        Color.White,
                                        shape = RoundedCornerShape(10.dp))
                                        .border(
                                            width = 1.dp,
                                            color = colorSecondary,
                                            shape = RoundedCornerShape(10.dp)
                                        ),
                                    colors = TextFieldDefaults.colors().copy(
                                        focusedContainerColor = colorPrimaryVariant,
                                        focusedTextColor = textColorSecondary,
                                        unfocusedTextColor = textColorSecondary,
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedContainerColor = colorPrimaryVariant,
                                        unfocusedIndicatorColor = Color.Transparent
                                    )
                                )
                            } else {
                                Text(
                                    text = amount,
                                    fontSize = 18.sp,
                                    color = textColorSecondary,
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