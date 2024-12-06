package com.example.flobizassignment.presentation.screens.expense

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.flobizassignment.R
import com.example.flobizassignment.presentation.components.topbar.AddExpenseTopBar
import com.example.flobizassignment.presentation.screens.expense.viewmodel.ViewEditExpenseViewModel
import com.example.flobizassignment.presentation.theme.FlobizAssignmentTheme
import com.example.flobizassignment.presentation.theme.background
import com.example.flobizassignment.presentation.theme.colorControlNormal
import com.example.flobizassignment.presentation.theme.colorSecondary
import com.example.flobizassignment.presentation.theme.textColorPrimary
import com.example.flobizassignment.presentation.theme.textColorSecondary
import com.example.flobizassignment.presentation.utils.Utils
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddExpenseScreen(
    navController: NavController,
    auth: FirebaseAuth,
    addExpenseViewModel: ViewEditExpenseViewModel = hiltViewModel(),
) {

    val datePickerState = rememberDatePickerState()
    val selectedLocalDate = datePickerState.selectedDateMillis?.let {
        Utils.convertMillisToDate(it)
    }

    val formattedDateString = selectedLocalDate?.let {
        Utils.formatDateToString(it)
    } ?: LocalDate.now().toString()

    val datePickerVisibilityState = remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            AddExpenseTopBar()
        },
        content = { padding ->
            val radioOptions = listOf("Expense", "Income")
            val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }

            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .background(background)
            ) {
                RadioOptionsRow(
                    options = radioOptions,
                    selectedOption = selectedOption,
                    onOptionSelected = onOptionSelected
                )

                Spacer(modifier = Modifier.height(10.dp))

                DatePickerField(
                    datePickerShowing = datePickerVisibilityState,
                    formattedDateString = formattedDateString
                )

                Spacer(modifier = Modifier.height(10.dp))

                DescriptionField()

                Spacer(modifier = Modifier.height(10.dp))

                TotalAmountField()
            }
        }
    )
}

@Composable
fun RadioOptionsRow(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 6.dp,
                vertical = 20.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        options.forEach { option ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .background(
                        color = if (option == selectedOption) Color.Transparent else Color.White,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = if (option == selectedOption) colorSecondary else colorControlNormal,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clickable { onOptionSelected(option) }
                    .padding(vertical = 15.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = (option == selectedOption),
                        onClick = null,
                        colors = RadioButtonDefaults.colors(
                            selectedColor = colorSecondary,
                            unselectedColor = Color.Gray
                        )
                    )

                    Spacer(modifier = Modifier.width(15.dp))

                    Text(
                        text = option,
                        fontWeight = FontWeight.Bold,
                        color = textColorSecondary
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(
    datePickerShowing: MutableState<Boolean>,
    formattedDateString: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            Text(
                text = "DATE",
                fontSize = 13.sp,
                color = textColorPrimary,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clickable { datePickerShowing.value = true }
                    .background(Color.White, RoundedCornerShape(10.dp))
                    .border(
                        width = 1.dp,
                        color = colorControlNormal,
                        shape = RoundedCornerShape(10.dp)
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (datePickerShowing.value) {
                    DatePickerDialog(
                        colors = DatePickerDefaults.colors(background),
                        onDismissRequest = { datePickerShowing.value = false },
                        confirmButton = {
                            Button(onClick = { datePickerShowing.value = false }) {
                                Text(text = "OK")
                            }
                        },
                        dismissButton = {
                            Button(onClick = { datePickerShowing.value = false }) {
                                Text(text = "Cancel")
                            }
                        }
                    ) {
                        DatePicker(
                            state = rememberDatePickerState(),
                            showModeToggle = true
                        )
                    }
                }
                if (formattedDateString.isEmpty()) {
                    Text(
                        "Select Date",
                        color = Color.Gray,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    Text(
                        text = formattedDateString,
                        color = textColorSecondary,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 20.dp)
                    )
                }

                Image(
                    modifier = Modifier.padding(end = 20.dp),
                    painter = painterResource(R.drawable.ic_calender),
                    contentDescription = "calendar"
                )
            }
        }
    }
}

@Composable
fun DescriptionField() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            Text(
                text = "DESCRIPTION",
                fontSize = 13.sp,
                color = textColorPrimary,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.padding( top = 20.dp))

            Column(
                modifier = Modifier.fillMaxWidth()
                    .height(50.dp)
                    .background(Color.White, RoundedCornerShape(10.dp))
                    .border(
                        width = 1.dp,
                        color = colorControlNormal,
                        shape = RoundedCornerShape(10.dp)
                    ),
                verticalArrangement = Arrangement.Center
            ) {
                BasicTextField(
                    textStyle = TextStyle(
                        color = textColorSecondary,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(start = 20.dp),
                    value = "Electric Bill",
                    onValueChange = {},
                )
            }
        }
    }
}

@Composable
fun TotalAmountField() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Total Amount",
                fontSize = 16.sp,
                color = textColorPrimary,
                fontWeight = FontWeight.Bold
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "₹",
                    fontSize = 15.sp,
                    color = textColorSecondary,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(10.dp))

                BasicTextField(
                    textStyle = TextStyle(
                        color = textColorSecondary,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    value = "3500",
                    onValueChange = {},
                )
            }
        }
    }
}