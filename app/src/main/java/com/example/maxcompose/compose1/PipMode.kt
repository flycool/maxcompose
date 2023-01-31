package com.example.maxcompose.compose1

import android.app.PendingIntent
import android.app.PictureInPictureParams
import android.app.RemoteAction
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Build
import android.util.Rational
import android.widget.VideoView
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toAndroidRect
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.maxcompose.MainActivity
import com.example.maxcompose.R
import com.example.maxcompose.viewmodel.PipModeTimeViewModel
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun PipMode() {
    val context = LocalContext.current

    AndroidView(
        factory = {
            VideoView(context).apply {
                setVideoURI(Uri.parse("android.resource://${context.packageName}/${R.raw.sample}"))
                start()
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned {
                videoViewBounds = it
                    .boundsInWindow()
                    .toAndroidRect()
            }
    )

}

private var videoViewBounds = Rect()
class MyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        println("clicked on PIP action")
    }
}

fun updatePipParams(context: Context): PictureInPictureParams? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        PictureInPictureParams.Builder()
            .setSourceRectHint(videoViewBounds)
            .setAspectRatio(Rational(16, 9))
            .setActions(
                listOf(
                    RemoteAction(
                        Icon.createWithResource(
                            context,
                            R.drawable.baseline_baby_changing_station_24
                        ),
                        "baby station",
                        "baby station",
                        PendingIntent.getBroadcast(
                            context, 0, Intent(context, MyReceiver::class.java),
                            PendingIntent.FLAG_IMMUTABLE
                        )
                    )
                )
            )
            .build()
    } else null
}