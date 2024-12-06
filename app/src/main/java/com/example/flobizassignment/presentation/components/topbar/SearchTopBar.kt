package com.example.flobizassignment.presentation.components.topbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flobizassignment.R
import com.example.flobizassignment.presentation.theme.colorPrimaryVariant
import com.example.flobizassignment.presentation.theme.textColorSecondary

@Composable
fun SearchTopBar(
    isSearching: Boolean,
    searchQuery: String,
    onIsSearchingChange: (Boolean) -> Unit,
    onSearchQueryChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth()
            .background(Color.White),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
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

                if (isSearching) {
                    TextField(
                        value = searchQuery,
                        onValueChange = { onSearchQueryChange(it) },
                        placeholder = {
                            Text("Search", color = textColorSecondary)
                        },
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = textColorSecondary,
                            unfocusedTextColor = textColorSecondary,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                colorPrimaryVariant,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(horizontal = 8.dp),
                        singleLine = true
                    )
                } else {
                    Text(
                        text = "Search",
                        color = textColorSecondary,
                        fontSize = 14.sp,
                    )
                }
            }
        }
    }
}