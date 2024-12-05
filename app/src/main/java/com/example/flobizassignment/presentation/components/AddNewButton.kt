package com.example.flobizassignment.presentation.components

import androidx.compose.foundation.layout.padding
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
import com.example.flobizassignment.presentation.theme.colorPrimary

@Composable
fun AddNewButton(modifier: Modifier = Modifier) {
    ExtendedFloatingActionButton(
        icon = { Icon(Icons.Filled.Add, contentDescription = "Add New Button") },
        text = { Text(text = "Add New") },
        contentColor = Color.White,
        containerColor = colorPrimary,
        shape = RoundedCornerShape(30.dp),
        modifier = modifier.width(180.dp),
        onClick = { }
    )
}