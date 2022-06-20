package com.kevin.rhodesislandassist.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.kevin.rhodesislandassist.R

@OptIn(ExperimentalUnitApi::class)
@Composable
fun Welcome() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painterResource(id = R.drawable.ic_logo),
            contentDescription = null,
            modifier = Modifier
                .scale(1.7f)
                .padding(0.dp, 80.dp, 0.dp, 0.dp)
        )
        Text(
            text = stringResource(id = R.string.word_welcome),
            style = TextStyle(fontSize = TextUnit(30f, TextUnitType.Sp)),
            modifier = Modifier.padding(10.dp, 100.dp, 10.dp, 0.dp)
        )
    }
}