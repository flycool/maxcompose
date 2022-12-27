package com.example.maxcompose.model

import com.example.maxcompose.destinations.*

data class ComposeItem(
    val name: String,
    val destination: DirectionDestination,
)

val composeItemList = listOf(
    ComposeItem("LazyVerticalGrid32", LazyVerticalGrid32Destination),
    ComposeItem("NavigationDrawer31", NavigationDrawer31Destination),
    ComposeItem("BottomSheet30", BottomSheet30Destination),
    ComposeItem("PageList29", PageList29Destination),
    ComposeItem("MotionLayout28", MotionLayout28Destination),
    ComposeItem("LayoutForAllScreenSize27", LayoutForAllScreenSize27Destination),
    ComposeItem("MigrateToCompose24", MigrateToCompose24Destination),
    ComposeItem("ComposePermission23", ComposePermission23Destination),
    ComposeItem("MultiSelected22", MultiSelected22Destination),
    ComposeItem("MultiLayerParallaxScroll21", MultiLayerParallaxScroll21Destination),
    ComposeItem("BottomSheetWithBadge20", BottomSheetWithBadge20Destination),
    ComposeItem("Navigation19", Navigation19Destination),
    ComposeItem("ProfileScreen17", ProfileScreen17Destination),
    ComposeItem("DropDown16", DropDown16Destination),
    ComposeItem("Timer15", Timer15Destination),
    ComposeItem("MeditationScreen14", MeditationScreen14Destination),
    ComposeItem("MusicKnob13", MusicKnob13Destination),
    ComposeItem("CircularProgressBar12", CircularProgressBar12Destination),
    ComposeItem("SimpleAnimation11", SimpleAnimation11Destination),
    ComposeItem("ConstraintLayout9", ConstraintLayout9Destination),
    ComposeItem("List8", List8Destination),
    ComposeItem("SimpleUI7", SimpleUI7Destination),
)