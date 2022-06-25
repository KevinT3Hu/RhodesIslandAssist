package com.kevin.rhodesislandassist.ui.component.widget

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.atan2

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PieChart(
    pieSlices: List<PieSlice>,
    modifier: Modifier = Modifier,
    onSliceClick: (PieSlice) -> Unit = {},
    radius: Dp = 100.dp
) {
    var centerOffset by remember { mutableStateOf(Offset(0f, 0f)) }
    var total by remember { mutableStateOf(0f) }
    Canvas(
        modifier = modifier
            .width(radius.times(2))
            .height(radius.times(2))
            .pointerInput("PieChart") {
                detectTapGestures { offset ->
                    val x = offset.x - centerOffset.x
                    val y = -offset.y + centerOffset.y
                    Log.i("piechart", "${x}:${y}")
                    var angle = getAngle(x, y)
                    for (slice in pieSlices) {
                        angle -= slice.number * 360 / total
                        if (angle < 0) {
                            onSliceClick(slice)
                            break
                        }
                    }
                }
            }) {
        //get overall numbers
        centerOffset = center
        total = 0f
        pieSlices.forEach {
            total += it.number
        }
        var startAngle = 0f
        pieSlices.forEachIndexed { _, pieSlice ->
            val sweepAngle = pieSlice.number * 360 / total
            drawArc(pieSlice.color, -startAngle, -sweepAngle, true)
            startAngle += sweepAngle
        }
    }
}

data class PieSlice(
    val name: String,
    val number: Float,
    val color: Color
)

private fun getAngle(x: Float, y: Float): Float {
    val angle = atan2(y, x) * 180 / PI.toFloat()
    return if (angle > 0) angle else angle + 360
}