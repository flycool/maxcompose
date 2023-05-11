package com.example.maxcompose.compose2.audiorecorder

import java.io.File

interface AudioRecorder {
    fun start(outputFile: File)
    fun stop()
}