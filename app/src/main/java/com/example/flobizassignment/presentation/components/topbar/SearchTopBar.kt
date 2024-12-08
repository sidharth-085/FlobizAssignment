package com.example.flobizassignment.presentation.components.topbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flobizassignment.R
import com.example.flobizassignment.presentation.theme.colorControlNormal
import com.example.flobizassignment.presentation.theme.colorPrimaryVariant
import com.example.flobizassignment.presentation.theme.textColorSecondary

@Composable
fun SearchTopBar(
    searchQuery: String,
    onIsSearchingChange: (Boolean) -> Unit,
    onSearchQueryChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp, top = 12.dp, bottom = 12.dp)
                .background(
                    color = colorPrimaryVariant,
                    shape = RoundedCornerShape(10.dp)
                )
                .clickable { onIsSearchingChange(true) }
        ) {
            Row(
                modifier = Modifier
                    .height(45.dp)
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(50.dp)
                        .padding(end = 10.dp),
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = "Image",
                )
                BasicTextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    textStyle = TextStyle(
                        color = textColorSecondary,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                        lineHeight = 16.sp
                    ),
                    modifier = Modifier
                        .fillMaxSize(),
                    decorationBox = { innerTextField ->
                        Box(
                            contentAlignment = Alignment.CenterStart,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            if (searchQuery.isEmpty()) {
                                Text(
                                    text = "Search",
                                    color = Color.Gray,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }
        }
    }
}