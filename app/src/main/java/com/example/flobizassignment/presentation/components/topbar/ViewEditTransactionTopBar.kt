package com.example.flobizassignment.presentation.components.topbar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.flobizassignment.R
import com.example.flobizassignment.domain.models.Transaction
import com.example.flobizassignment.presentation.screens.transaction.viewmodel.ViewEditTransactionViewModel
import com.example.flobizassignment.presentation.utils.Utils

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ViewEditTransactionTopBar(
    transaction: Transaction,
    navController: NavController,
    edit: Boolean,
    viewTransactionViewModel: ViewEditTransactionViewModel = hiltViewModel(),
    changeEditMode: (value: Boolean) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(24.dp)
                    .padding(top = 2.dp)
                    .clickable {
                        if (edit) {
                            changeEditMode(false)
                        } else {
                            navController.popBackStack()
                        }
                    },
                painter = painterResource(R.drawable.ic_arrow_left),
                contentDescription = "Back Button"
            )

            Spacer(modifier = Modifier.width(20.dp))

            Text(
                text = "Expense",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 2.dp)
            )

            Row(
                modifier = Modifier.padding(end = 10.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                if (edit) {
                    Image(
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                viewTransactionViewModel.updateTransaction(
                                    updatedTransaction = transaction,
                                    onSuccessCallback = {
                                        changeEditMode(false)
                                    },
                                    onFailureCallback = { e ->
                                        Utils.onError(e)
                                    }
                                )
                            },
                        painter = painterResource(R.drawable.ic_check),
                        contentDescription = "save"
                    )
                } else {
                    Image(
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { changeEditMode(true) },
                        painter = painterResource(R.drawable.ic_edit),
                        contentDescription = "edit"
                    )
                }

                Spacer(modifier = Modifier.width(25.dp))

                Image(
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            viewTransactionViewModel.deleteTransaction(
                                transaction = transaction,
                                onSuccessCallback = {
                                    navController.popBackStack()
                                },
                                onFailureCallback = { e ->
                                    Utils.onError(e)
                                }
                            )
                        },
                    painter = painterResource(R.drawable.ic_delete),
                    contentDescription = "delete"
                )
            }
        }
    }
}