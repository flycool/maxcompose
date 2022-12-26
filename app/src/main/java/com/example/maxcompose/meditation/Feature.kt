package com.example.maxcompose.meditation

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.example.maxcompose.R
import com.example.maxcompose.ui.theme.*

data class Feature(
    val title: String,
    @DrawableRes val iconId: Int,
    val lightColor: Color,
    val mediumColor: Color,
    val darkColor: Color,
)

val features = listOf(
    Feature(
        title = "Sleep meditation",
        iconId = R.drawable.ic_headphone,
        lightColor = BlueViolet1,
        mediumColor = BlueViolet2,
        darkColor = BlueViolet3
    ),
    Feature(
        title = "Tips for sleeping",
        iconId = R.drawable.ic_videocam,
        lightColor = LightGreen1,
        mediumColor = LightGreen2,
        darkColor = LightGreen3
    ),
    Feature(
        title = "Night island",
        iconId = R.drawable.ic_headphone,
        lightColor = OrangeYellow1,
        mediumColor = OrangeYellow2,
        darkColor = OrangeYellow3
    ),
    Feature(
        title = "Calming sounds",
        iconId = R.drawable.ic_headphone,
        lightColor = Beige1,
        mediumColor = Beige2,
        darkColor = Beige3
    ),

    )