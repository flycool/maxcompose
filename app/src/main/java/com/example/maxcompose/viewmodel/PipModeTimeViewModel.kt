package com.example.maxcompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class PipModeTimeViewModel: ViewModel() {

    private val timeMillis = MutableStateFlow("00:00:00")
    val time = timeMillis

    private val _started = MutableStateFlow(false)
    val started = _started.asStateFlow()

    private val sdf = SimpleDateFormat("hh:mm:ss:SS", Locale.CHINA)

    private var job: Job? = null

    fun startOrPause() {
        if (_started.value) {
            _started.value = false
            job?.cancel()
        } else {
            _started.value = true
            job = viewModelScope.launch { start() }
        }
    }

    private suspend fun CoroutineScope.start() {
        while (isActive) {
            timeMillis.update {
                sdf.format(it)
            }
            awaitFrame()
        }
    }

}