package com.example.maxcompose.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maxcompose.compose2.network.NetworkConnectivityServiceImpl
import com.example.maxcompose.compose2.network.NetworkStatus
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class NetworkConnectivityVewModel(
    context: Context
): ViewModel() {

    private val networkConnectivityService  = NetworkConnectivityServiceImpl(context)

    val networkStatus: StateFlow<NetworkStatus> = networkConnectivityService.networkStatus.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        NetworkStatus.Unknown
    )
}