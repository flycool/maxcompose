package com.example.maxcompose.compose2.audiorecorder

import java.io.File

interface AudioPlayer {
    fun playFile(file:File)
    fun stop()
}