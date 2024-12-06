package com.example.flobizassignment.domain.models

import androidx.annotation.DrawableRes

data class BottomNavItem(
    @DrawableRes val icon: Int,
    val name: String
)