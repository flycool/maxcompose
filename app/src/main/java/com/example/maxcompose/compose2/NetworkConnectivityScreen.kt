package com.example.maxcompose.compose2

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.maxcompose.compose2.network.NetworkStatus
import com.example.maxcompose.viewmodel.NetworkConnectivityVewModel
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun NetworkConnectivityScreen(
    snackbarHostState: SnackbarHostState = SnackbarHostState()
) {

    val context = LocalContext.current
    val viewModel: NetworkConnectivityVewModel =
        viewModel(factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return NetworkConnectivityVewModel(context) as T
            }
        })
    val networkStatus = viewModel.networkStatus.collectAsState()
    if (networkStatus.value == NetworkStatus.Disconnected) {
        LaunchedEffect(networkStatus) {
            snackbarHostState.showSnackbar("you are offline")
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Connectivity Service: ${networkStatus.value}")
        }
    }
}