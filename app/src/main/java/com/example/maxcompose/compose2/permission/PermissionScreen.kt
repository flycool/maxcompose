package com.example.maxcompose.compose2.permission

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination

@RequiresApi(Build.VERSION_CODES.M)
@Composable
@Destination
fun PermissionScreen() {
    val viewModel = viewModel<PermissionViewModel>()
    val dialogQueue = viewModel.visiblePermissionDialogQueue

    val cameraPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            viewModel.onPermissionResult(
                permission = Manifest.permission.CAMERA,
                isGranted = isGranted
            )
        }
    )

    val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { perms ->
            permissionsToRequest.forEach { permission ->
                viewModel.onPermissionResult(
                    permission = permission,
                    isGranted = perms[permission] == true
                )
            }
        }
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            cameraPermissionResultLauncher.launch(
                Manifest.permission.CAMERA
            )
        }) {
            Text(text = "request one permission")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            multiplePermissionResultLauncher.launch(permissionsToRequest)
        }) {
            Text(text = "request multiple permission")
        }
    }

    val context: Activity = LocalContext.current as Activity
    dialogQueue
        .reversed()
        .forEach { permission ->
            PermissionDialog(
                permissionTextProvider = when (permission) {
                    Manifest.permission.CAMERA -> {
                        CameraPermissionTextProvider()
                    }
                    Manifest.permission.RECORD_AUDIO -> {
                        RecordAudioPermissionTextProvider()
                    }
                    Manifest.permission.CALL_PHONE -> {
                        PhoneCallPermissionTextProvider()
                    }
                    else -> return@forEach
                },
                isPermanentlyDeclined = !context.shouldShowRequestPermissionRationale(permission),
                onDismiss = viewModel::dismissDialog,
                onOkClick = {
                    viewModel.dismissDialog()
                    multiplePermissionResultLauncher.launch(arrayOf(permission))
                },
                onGoToAppSettingsClick = context::openAppSettings
            )
        }

}

val permissionsToRequest = arrayOf(
    Manifest.permission.RECORD_AUDIO,
    Manifest.permission.CALL_PHONE,
)

fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}
























