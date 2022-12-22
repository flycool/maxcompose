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
    ComposeItem("MotionLayout28", MotionLayout28Destination),
    ComposeItem("LayoutForAllScreenSize27", LayoutForAllScreenSize27Destination),
    ComposeItem("MigrateToCompose24", MigrateToCompose24Destination),
    ComposeItem("ComposePermission23", ComposePermission23Destination),
    ComposeItem("MultiSelected22", MultiSelected22Destination),
    ComposeItem("MultiLayerParallaxScroll21", MultiLayerParallaxScroll21Destination),
    ComposeItem("BottomSheetWithBadge20", BottomSheetWithBadge20Destination),
    ComposeItem("Navigation19", Navigation19Destination),
    ComposeItem("DropDown16", DropDown16Destination),
    ComposeItem("CircularProgressBar12", CircularProgressBar12Destination),
    ComposeItem("Home11", Home11Destination),
    ComposeItem("Home9", Home9Destination),
    ComposeItem("Home8", Home8Destination),
    ComposeItem("Home7", Home7Destination),
)