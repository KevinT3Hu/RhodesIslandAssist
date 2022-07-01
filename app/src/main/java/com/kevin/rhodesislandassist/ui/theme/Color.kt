package com.kevin.rhodesislandassist.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorMatrix

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val DividerColor = Color(
    Color.Gray.red,
    Color.Gray.green,
    Color.Gray.blue,
    0.2f
)

fun rarityColor(rarity: Int, isDarkMode: Boolean = false) = when (rarity) {
    2 -> Color.Gray
    3 -> Color(147, 112, 219)
    4 -> Color.Red
    5 -> Color(255, 140, 0)
    else -> if (isDarkMode) Color.White else Color.Black
}

val ReverseColor = ColorMatrix(
    floatArrayOf(
        -1f, 0f, 0f, 0f, 255f,
        0f, -1f, 0f, 0f, 255f,
        0f, 0f, -1f, 0f, 255f,
        0f, 0f, 0f, 1f, 0f
    )
)