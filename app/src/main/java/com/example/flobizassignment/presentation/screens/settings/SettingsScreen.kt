package com.example.flobizassignment.presentation.screens.settings

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flobizassignment.R
import com.example.flobizassignment.presentation.theme.FlobizAssignmentTheme
import com.example.flobizassignment.presentation.theme.background
import com.example.flobizassignment.presentation.theme.textColorPrimary

@Composable
fun SettingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
    ) {
        Column (
            modifier = Modifier.fillMaxWidth()
                .padding(
                    top = 40.dp,
                    start = 20.dp,
                    end = 20.dp
                )
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(5.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_logout),
                    contentDescription = "Logout Icon",
                    modifier = Modifier.padding(end = 10.dp)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "Logout",
                    color = textColorPrimary,
                    fontSize = 12.sp,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    FlobizAssignmentTheme {
        SettingsScreen()
    }
}