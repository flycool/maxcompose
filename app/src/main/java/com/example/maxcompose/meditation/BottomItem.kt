package com.example.maxcompose.meditation

import androidx.annotation.DrawableRes
import com.example.maxcompose.R

data class BottomItem(
    val name: String,
    @DrawableRes val iconId: Int
)

val bottomItems = listOf(
    BottomItem(
        name = "Home",
        iconId = R.drawable.ic_home
    ),
    BottomItem(
        name = "Meditation",
        iconId = R.drawable.ic_bubble
    ),
    BottomItem(
        name = "Sleep",
        iconId = R.drawable.ic_moon
    ),
    BottomItem(
        name = "Music",
        iconId = R.drawable.ic_music
    ),
    BottomItem(
        name = "Profile",
        iconId = R.drawable.ic_profile
    ),
)