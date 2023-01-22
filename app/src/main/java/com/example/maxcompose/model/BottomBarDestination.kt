package com.example.maxcompose.model

import com.example.destinations.Compose1HomeDestination
import com.example.destinations.Compose2HomeDestination
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    val label: String
) {

    Compose1Home(Compose1HomeDestination, "Compose1"),
    Compose2Home(Compose2HomeDestination, "Compose2"),
}