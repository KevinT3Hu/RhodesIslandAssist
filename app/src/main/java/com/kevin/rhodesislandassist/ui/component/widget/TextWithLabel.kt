package com.kevin.rhodesislandassist.ui.component.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType

@OptIn(ExperimentalUnitApi::class)
@Composable
fun TextWithLabel(
    modifier: Modifier=Modifier,
    labelColor:Color=MaterialTheme.colorScheme.onBackground,
    textColor: Color=MaterialTheme.colorScheme.onBackground,
    label:String,
    text:String
){
    Column(horizontalAlignment = Alignment.Start, modifier = modifier.fillMaxWidth()) {
        Text(text = label, color = labelColor, fontSize = TextUnit(15f, TextUnitType.Sp))
        Text(text = text, color = textColor, fontSize = TextUnit(15f, TextUnitType.Sp))
    }
}

@Preview
@Composable
fun TestTextWithLabel() {
    TextWithLabel(label = "label", text = "text")
}