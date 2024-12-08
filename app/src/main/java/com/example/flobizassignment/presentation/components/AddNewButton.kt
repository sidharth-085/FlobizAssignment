package com.example.flobizassignment.presentation.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flobizassignment.presentation.theme.colorPrimary

@Composable
fun AddNewButton(onClick: () -> Unit) {
    ExtendedFloatingActionButton(
        icon = { Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Add New Button",
            Modifier.size(15.dp)
        ) },
        text = { Text(
            text = "Add New",
            fontSize = 12.sp
        ) },
        contentColor = Color.White,
        containerColor = colorPrimary,
        shape = RoundedCornerShape(30.dp),
        modifier = Modifier.width(160.dp).height(40.dp),
        onClick = onClick
    )
}