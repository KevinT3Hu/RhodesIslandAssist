package com.kevin.rhodesislandassist.ui.component.widget

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.math.exp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun ExpandableCard(
    cardContent: @Composable ColumnScope.() -> Unit,
    cardExpandedContent: @Composable ColumnScope.() -> Unit,
    modifier: Modifier=Modifier,
    animationDurationMillis: Int=300,
    dividerColor:Color=Color.Gray
) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = TweenSpec(
                    durationMillis = animationDurationMillis,
                    easing = LinearOutSlowInEasing
                )
            )
            .clickable { expanded = !expanded }
    ) {
        cardContent()
        AnimatedContent(targetState = expanded) {targetState ->
            if (targetState){
                Column(modifier=Modifier.fillMaxWidth()) {
                    Divider(modifier= Modifier
                        .padding(3.dp)
                        .fillMaxWidth(), color = dividerColor)
                    cardExpandedContent()
                }
            }
        }
    }
}