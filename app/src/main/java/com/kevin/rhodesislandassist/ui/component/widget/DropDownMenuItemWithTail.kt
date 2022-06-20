package com.kevin.rhodesislandassist.ui.component.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.*

@OptIn(ExperimentalUnitApi::class)
@Composable
fun DropDownMenuItemWithTail(
    text: String,
    tail: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    textPaddingStart: Dp = 0.dp,
    tailPaddingEnd: Dp = 0.dp,
    onClick: () -> Unit = {}
) {
    DropdownMenuItem(text = {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = text,
                modifier = Modifier
                    .padding(start = textPaddingStart)
                    .weight(1.1f),
                fontSize = TextUnit(15f, TextUnitType.Sp)
            )
            Box(modifier = Modifier
                .padding(end = tailPaddingEnd)
                .weight(0.5f)) {
                tail()
            }
        }
    }, onClick = {
        onClick()
    }, modifier = modifier.fillMaxWidth())
}