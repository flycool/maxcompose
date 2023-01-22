package com.example.maxcompose2

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.isUnspecified
import androidx.core.widget.TextViewCompat.AutoSizeTextType
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun AutoReSizedText() {
    AutoReSizedText(text = "Hello")
}

@Composable
fun AutoReSizedText(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = MaterialTheme.typography.body1,
    color: Color = MaterialTheme.colors.onSurface,
) {
    var resizedTextStyle by remember {
        mutableStateOf(style)
    }

    var shouldDraw by remember {
        mutableStateOf(false)
    }

    val defaultFontSize = MaterialTheme.typography.body1.fontSize

    Text(
        modifier = modifier.drawWithContent {
            if (shouldDraw) {
                drawContent()
            }
        },
        text = text,
        color = color,
        softWrap = false,
        style = resizedTextStyle,
        onTextLayout = { result ->
            if (result.didOverflowWidth) {
                if (style.fontSize.isUnspecified) {
                    resizedTextStyle = resizedTextStyle.copy(
                        fontSize = defaultFontSize
                    )
                }
                resizedTextStyle = resizedTextStyle.copy(
                    fontSize = resizedTextStyle.fontSize * 0.95
                )
            } else {
                shouldDraw = true
            }
        }
    )

}