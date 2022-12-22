package com.example.maxcompose.util

import android.content.res.Resources
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState


fun Float.toDp(): Dp = (this/Resources.getSystem().displayMetrics.density).dp

@OptIn(ExperimentalPermissionsApi::class)
fun PermissionState.isPermanentlyDenied(): Boolean =
    !hasPermission && !shouldShowRationale


@Composable
fun rememberWindowInfo() : WindowInfo{
    val configuration = LocalConfiguration.current
    return WindowInfo(
        screenWidthType = when  {
            configuration.screenWidthDp < 600 -> WindowInfo.WindowInfoType.Compact
            configuration.screenWidthDp > 840 -> WindowInfo.WindowInfoType.Medium
            else -> WindowInfo.WindowInfoType.Expanded
        },
        screenHeightType = when  {
            configuration.screenHeightDp < 480 -> WindowInfo.WindowInfoType.Compact
            configuration.screenHeightDp < 900 -> WindowInfo.WindowInfoType.Medium
            else -> WindowInfo.WindowInfoType.Expanded
        },
        screenWidth = configuration.screenWidthDp.dp,
        screenHeight = configuration.screenHeightDp.dp,
    )
}

data class WindowInfo(
    val screenWidthType: WindowInfoType,
    val screenHeightType: WindowInfoType,
    val screenWidth: Dp,
    val screenHeight: Dp,
) {
    sealed class  WindowInfoType {
        object Compact: WindowInfoType()
        object Medium: WindowInfoType()
        object Expanded: WindowInfoType()
    }
}