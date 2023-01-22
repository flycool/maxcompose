package com.example.maxcompose2

import android.app.WallpaperManager
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

@Destination
@Composable
fun SetWallpaper(
    modifier: Modifier = Modifier
) {
    val url = "https://img2.baidu.com/it/u=616172321,1682314558&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=500"
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(context)
                        .crossfade(true)
                        .data(url)
                        .size(Size.ORIGINAL)
                        .build()
                ),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
            )

            Button(
                onClick = {
                    val wallpaperManager = WallpaperManager.getInstance(context)
                    try {
                        coroutineScope.launch {
//                            val task = async(Dispatchers.IO) {
//                                BitmapFactory.decodeStream(
//                                    URL(url).openConnection().getInputStream()
//                                )
//                            }
//                            val bitmap = task.await()

                            val bitmap = withContext(Dispatchers.IO) {
                                BitmapFactory.decodeStream(
                                    URL(url).openConnection().getInputStream()
                                )
                            }

                            wallpaperManager.setBitmap(bitmap)

                            Toast.makeText(context, "success", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text(text = "set home and lock wallpaper")
            }
        }
    }
}