package com.example.flobizassignment.presentation.screens.transaction

import android.os.Build
import android.widget.Toast
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.flobizassignment.R
import com.example.flobizassignment.domain.models.Transaction
import com.example.flobizassignment.presentation.components.topbar.AddTransactionTopBar
import com.example.flobizassignment.presentation.screens.transaction.viewmodel.AddTransactionViewModel
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
fun AddTransactionScreen(
    navController: NavController,
    auth: FirebaseAuth,
    addTransactionViewModel: AddTransactionViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    val datePickerState = rememberDatePickerState()
    val selectedLocalDate = datePickerState.selectedDateMillis?.let {
        Utils.convertMillisToDate(it)
    }

    val formattedDateString = selectedLocalDate?.let {
        Utils.formatDateToString(it)
    } ?: LocalDate.now().toString()

    var datePickerVisibilityState by remember {
        mutableStateOf(false)
    }

    val uiState by addTransactionViewModel.stateFlow.collectAsState()

    var description by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    val isButtonEnabled = formattedDateString.isNotEmpty() && description.isNotEmpty() && amount.isNotEmpty()

    val options = listOf("Expense", "Income")

    var selectedOption by remember { mutableStateOf(options[0]) }
    val onOptionSelected: (String) -> Unit = { selectedOption = it }

    LaunchedEffect(uiState) {
        when (uiState) {
            is TransactionState.Completed -> {
                Toast.makeText(
                    context,
                    "Transaction Added",
                    Toast.LENGTH_SHORT
                ).show()
            }

            is TransactionState.Failed -> {
                Toast.makeText(
                    context,
                    "Something went wrong",
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> Unit
        }
    }

    Scaffold(
        topBar = {
            AddTransactionTopBar()
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .background(background)
            ) {
                RadioOptionsRow(
                    options = options,
                    selectedOption = selectedOption,
                    onOptionSelected = onOptionSelected
                )


                DatePickerField(
                    datePickerShowing = datePickerVisibilityState,
                    onDatePickerShowChange = { change ->
                        datePickerVisibilityState = change
                    },
                    dateState = datePickerState,
                    dateToString = formattedDateString
                )

                Spacer(modifier = Modifier.height(10.dp))

                DescriptionField(
                    description = description,
                    onDescriptionChange = { change ->
                        description = change
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))

                TotalAmountField(
                    amount = amount,
                    onAmountChange = { change ->
                        if (change.all { it.isDigit() }) {
                            amount = change
                        }
                    }
                )
            }
        },
        bottomBar = {
            Box(
                modifier = Modifier.background(Color.White).padding(top = 10.dp),
                contentAlignment = Alignment.BottomCenter
            ) {

                Button(
                    onClick = {
                        val transaction = Transaction(
                            date = formattedDateString,
                            description = description,
                            amount = amount.toDoubleOrNull() ?: 0.0,
                            type = selectedOption,
                            id = auth.currentUser!!.uid
                        )
                        addTransactionViewModel.saveTransaction(transaction)
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp, end = 15.dp, bottom = 5.dp)
                        .size(45.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorSecondary),
                    enabled = isButtonEnabled,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Save",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
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
                        shape = RoundedCornerShape(8.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = if (option == selectedOption) colorSecondary else colorControlNormal,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable { onOptionSelected(option) }
                    .padding(top = 12.dp, bottom = 12.dp, end = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        modifier = Modifier.size(10.dp),
                        selected = (option == selectedOption),
                        onClick = null,
                        colors = RadioButtonDefaults.colors(
                            selectedColor = colorSecondary,
                            unselectedColor = Color.Gray
                        )
                    )

                    Spacer(modifier = Modifier.width(20.dp))

                    Text(
                        text = option,
                        fontWeight = FontWeight.Bold,
                        color = textColorSecondary,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(
    datePickerShowing: Boolean,
    onDatePickerShowChange: (Boolean) -> Unit,
    dateState: DatePickerState,
    dateToString: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(15.dp)
    ) {
        Text(
            text = "DATE",
            fontSize = 12.sp,
            color = textColorPrimary,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .clickable { onDatePickerShowChange(true) }
                .background(Color.White, RoundedCornerShape(10.dp))
                .border(1.dp, colorControlNormal, RoundedCornerShape(5.dp)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (datePickerShowing) {
                DatePickerDialog(
                    colors = DatePickerDefaults.colors(background),
                    onDismissRequest = { onDatePickerShowChange(false) },
                    confirmButton = {
                        Button(onClick = { onDatePickerShowChange(false) }) { Text("OK") }
                    },
                    dismissButton = {
                        Button(onClick = { onDatePickerShowChange(false) }) { Text("Cancel") }
                    }
                ) {
                    DatePicker(state = dateState, showModeToggle = true)
                }
            }
            Text(
                text = dateToString.ifEmpty { "Select Date" },
                color = if (dateToString.isEmpty()) Color.Gray else textColorSecondary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 15.dp, top = 2.dp)
            )
            Image(
                painter = painterResource(R.drawable.ic_calender),
                contentDescription = "calendar",
                modifier = Modifier.padding(end = 15.dp).size(22.dp)
            )
        }
    }
}

@Composable
fun DescriptionField(
    description: String,
    onDescriptionChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(15.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "DESCRIPTION",
            fontSize = 12.sp,
            color = textColorPrimary,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        BasicTextField(
            value = description,
            onValueChange = onDescriptionChange,
            textStyle = TextStyle(
                color = textColorSecondary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                lineHeight = 16.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(Color.White, RoundedCornerShape(5.dp))
                .border(1.dp, colorControlNormal, RoundedCornerShape(5.dp))
                .padding(horizontal = 15.dp),
            decorationBox = { innerTextField ->
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (description.isEmpty()) {
                        Text(
                            text = "Enter Description",
                            color = Color.Gray,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}

@Composable
fun TotalAmountField(
    amount: String,
    onAmountChange: (String) -> Unit
) {
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
                fontSize = 14.sp,
                color = textColorPrimary,
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier.fillMaxWidth(0.4f), // Restrict the width of this row
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End // Ensures alignment to the right
            ) {
                Text(
                    text = "₹",
                    fontSize = 15.sp,
                    color = textColorSecondary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 2.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                BasicTextField(
                    value = amount,
                    onValueChange = onAmountChange,
                    textStyle = TextStyle(
                        color = textColorSecondary,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .widthIn(min = 50.dp)
                        .align(Alignment.CenterVertically),
                    decorationBox = { textField ->
                        Box(
                            contentAlignment = Alignment.CenterStart,
                        ) {
                            if (amount.isEmpty()) {
                                Text(
                                    text = "Amount",
                                    color = Color.Gray,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            textField()
                        }
                    }
                )
            }
        }
    }
}