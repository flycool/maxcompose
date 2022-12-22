package com.example.maxcompose.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val name: String,
    val route:String,
    val icon:ImageVector,
    val badgeCount: Int = 0,
)

val bottomItems = listOf(
    BottomNavItem(
        name = "Home",
        route = "home",
        icon = Icons.Default.Home
    ),
    BottomNavItem(
        name = "Chat",
        route = "chat",
        icon = Icons.Default.Info,
        badgeCount = 125
    ),
    BottomNavItem(
        name = "Settings",
        route = "settings",
        icon = Icons.Default.Settings,
        badgeCount = 24
    )
)
