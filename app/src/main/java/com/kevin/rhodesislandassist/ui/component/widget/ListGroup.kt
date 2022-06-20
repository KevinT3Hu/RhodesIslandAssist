package com.kevin.rhodesislandassist.ui.component.widget

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalUnitApi::class)
@Composable
fun <T> ListGroup(
    data: List<T>,
    label: String,
    modifier: Modifier = Modifier,
    item: @Composable ColumnScope.(arg: T) -> Unit,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = TextUnit(10f, TextUnitType.Sp),
            modifier = Modifier.padding(start = 15.dp, bottom = 5.dp),
            color = Color.Gray
        )
        Divider(modifier = Modifier
            .fillMaxWidth()
            .height(0.3.dp), color = Color.Gray)
        Column {
            data.forEach {
                item(it)
                Divider(modifier = Modifier
                    .fillMaxWidth()
                    .height(0.3.dp), color = Color.Gray)
            }
        }
    }
}