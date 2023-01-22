package com.example.maxcompose2

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

const val TEST_VERIFY_CODE = "6768"

val textList = listOf(
    mutableStateOf(
        TextFieldValue(
            text = "",
            selection = TextRange(0),
        )
    ),
    mutableStateOf(
        TextFieldValue(
            text = "",
            selection = TextRange(0),
        )
    ),
    mutableStateOf(
        TextFieldValue(
            text = "",
            selection = TextRange(0),
        )
    ),
    mutableStateOf(
        TextFieldValue(
            text = "",
            selection = TextRange(0),
        )
    ),
)

val requestList = listOf(
    FocusRequester(),
    FocusRequester(),
    FocusRequester(),
    FocusRequester(),
)