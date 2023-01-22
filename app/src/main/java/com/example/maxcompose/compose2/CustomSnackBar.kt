package com.example.maxcompose2

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.maxcompose.R
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.launch

@Destination
@Composable
fun CustomSnackBar() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        val context = LocalContext.current

        val snackBarHostState = remember {
            SnackbarHostState()
        }

        val scope = rememberCoroutineScope()

        Button(onClick = {
            scope.launch {
                snackBarHostState.showSnackbar("")
            }
        }) {
            Text(text = "show snackbar")
        }

        SnackbarHost(
            hostState = snackBarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            //custom snackbar here
            CustomSnackBarData(
                title = "Coding with cat",
                content = "Android development tutorial step by step",
                profileImageResource = R.drawable.ic_launcher_background,
                actionImageResource = R.drawable.ic_launcher_background
            ) {
                Uri.parse("https://www.baidu.com").also {
                    val intent = Intent(Intent.ACTION_VIEW, it)
                    context.startActivity(intent)
                }
            }
        }
    }
}

@Composable
fun CustomSnackBarData(
    modifier: Modifier = Modifier,
    title: String,
    content: String,
    profileImageResource: Int,
    actionImageResource: Int,
    onAction: () -> Unit
) {
    Snackbar(
        elevation = 0.dp,
        backgroundColor = Color.Transparent
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(color = android.graphics.Color.parseColor("#00ceff")),
                                Color(color = android.graphics.Color.parseColor("#fb9c88"))
                            )
                        )
                    )
                    .padding(start = 78.dp, top = 8.dp, bottom = 12.dp, end = 8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = content,
                    color = Color.White,
                    fontStyle = FontStyle.Italic,
                    fontSize = 15.sp,
                )

            }

            Column(
                modifier = Modifier.padding(start = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    elevation = 6.dp,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = profileImageResource),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(50.dp)
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Image(
                    painter = painterResource(id = actionImageResource),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(30.dp)
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null,
                            onClick = onAction
                        )
                )
            }
        }
    }
}























