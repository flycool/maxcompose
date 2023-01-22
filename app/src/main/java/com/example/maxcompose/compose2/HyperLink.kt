package com.example.maxcompose2

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun HyperLink() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        val context = LocalContext.current

        val clickText = buildAnnotatedString {
            append("Hi, I'm your friend coding with cat.\\n")
            append("this is my ")

            pushStringAnnotation(
                tag = "channel",
                annotation = "http://www.baidu.com"
            )
            withStyle(
                style = SpanStyle(
                    color = Color.Blue,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            ) {
                append("channel.")
            }
            pop()

            append("\n\nand this is a video about custom snack bar.")

            pushStringAnnotation(
                tag = "snack bar video",
                annotation = "https://space.bilibili.com/28996856/?spm_id_from=333.999.0.0"
            )
            withStyle(
                style = SpanStyle(
                    color = Color.Blue,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            ) {
                append("a video about custom snack bar.")
            }
            pop()

            append(" I like it")
        }

        ClickableText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            text = clickText,
            onClick = { offset ->
                clickText.getStringAnnotations(
                    start = offset,
                    end = offset
                ).firstOrNull()?.let { annotation ->
                    if (annotation.tag == "channel" || annotation.tag == "snack bar video") {
                        Uri.parse(annotation.item).also {
                            context.startActivity(
                                Intent(Intent.ACTION_VIEW, it)
                            )
                        }
                    }
                }
            }
        )
    }

}