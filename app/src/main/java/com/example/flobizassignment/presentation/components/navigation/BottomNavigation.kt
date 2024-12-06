package com.example.flobizassignment.presentation.components.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import com.example.flobizassignment.R
import com.example.flobizassignment.domain.models.BottomNavItem
import com.example.flobizassignment.presentation.theme.colorSecondary

@Composable
fun BottomNavigation(
    items: List<BottomNavItem>,
    selectedItem: Int,
    onItemClick: (Int) -> Unit,
    backStackEntry: NavBackStackEntry?
) {
    Column (
        modifier = Modifier.fillMaxWidth().padding(
            bottom = WindowInsets.navigationBars.asPaddingValues()
                .calculateBottomPadding()
        ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NavigationBar(
                containerColor = Color.White,
            ) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItem == index,
                        onClick = { onItemClick(index) },
                        colors = NavigationBarItemColors(
                            selectedIconColor = Color.White,
                            selectedTextColor = Color.White,
                            selectedIndicatorColor = Color.White,
                            unselectedIconColor = Color.White,
                            unselectedTextColor = Color.White,
                            disabledIconColor = Color.White,
                            disabledTextColor = Color.White,
                        ),
                        icon = {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                            ) {
                                Image(
                                    painter = painterResource(id = item.icon),
                                    contentDescription = item.name,
                                    modifier = Modifier
                                        .size(25.dp)
                                        .graphicsLayer {
                                            alpha =
                                                if (backStackEntry?.destination?.route?.startsWith(item.route) == true) 1f else 0.5f
                                        },
                                    colorFilter = if (backStackEntry?.destination?.route?.startsWith(item.route) == true) null else ColorFilter.tint(Color.Gray)
                                )
                                Text(
                                    text = item.name,
                                    fontSize = 10.sp,
                                    color = if (backStackEntry?.destination?.route?.startsWith(item.route) == true) colorSecondary else Color.LightGray,
                                    textAlign = TextAlign.Center
                                )
                            }
                        },
                    )
                }
            }
        }
    }
}