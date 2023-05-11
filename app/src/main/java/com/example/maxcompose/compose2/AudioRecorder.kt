package com.example.maxcompose.compose2

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.maxcompose.compose2.audiorecorder.AndroidAudioPlayer
import com.example.maxcompose.compose2.audiorecorder.AndroidAudioRecorder
import com.ramcosta.composedestinations.annotation.Destination
import java.io.File

@Destination
@Composable
fun AudioRecorder(
    context: Context,
    recorder: AndroidAudioRecorder,
    player: AndroidAudioPlayer,
) {
    val audioFile by remember {
        mutableStateOf(File(context.cacheDir, "audio.mp3"))
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            recorder.start(audioFile)
        }) {
            Text(text = "record")
        }
        Button(onClick = {
            recorder.stop()
        }) {
            Text(text = "stop recording")
        }
        Button(onClick = {
            player.playFile(audioFile ?: return@Button)
        }) {
            Text(text = "play")
        }
        Button(onClick = {
            player.stop()
        }) {
            Text(text = "stop playing")
        }
    }

}