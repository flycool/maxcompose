package com.example.maxcompose.Instagramui

import androidx.annotation.DrawableRes
import com.example.maxcompose.R

data class ImageWithText(
    @DrawableRes val imageId: Int,
    val text: String,
)

val highlights = listOf<ImageWithText>(
    ImageWithText(
        imageId = R.drawable.youtube,
        text = "Youtube"
    ),
    ImageWithText(
        imageId = R.drawable.qa,
        text = "Q&A"
    ),
    ImageWithText(
        imageId = R.drawable.discord,
        text = "Discord"
    ),
    ImageWithText(
        imageId = R.drawable.telegram,
        text = "Telegram"
    ),
)

val postsViews = listOf(
    ImageWithText(
        imageId = R.drawable.ic_grid,
        text = "Posts"
    ),
    ImageWithText(
        imageId = R.drawable.ic_reels,
        text = "Reels"
    ),
    ImageWithText(
        imageId = R.drawable.ic_igtv,
        text = "IGTV"
    ),
    ImageWithText(
        imageId = R.drawable.profile,
        text = "Profile"
    ),
)

val posts = listOf(
    R.drawable.kmm,
    R.drawable.intermediate_dev,
    R.drawable.master_logical_thinking,
    R.drawable.bad_habits,
    R.drawable.multiple_languages,
    R.drawable.learn_coding_fast,
)