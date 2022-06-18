package com.kevin.rhodesislandassist.ui.component.widget

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NumberSelector(
    modifier: Modifier=Modifier,
    numberRange:IntRange=0..Int.MAX_VALUE,
    initialValue:Int=0,
    value:MutableState<Int>,
    onNumberChange:(Int)->Unit={ _: Int -> }
){
    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = {
            if (value.value-1 in numberRange){
                value.value--
                onNumberChange(value.value)
            }
        }) {
            Icon(Icons.Filled.Remove, contentDescription = null)
        }
        AnimatedContent(
            targetState = value,
            transitionSpec = {
                if (targetState.value>initialState.value){
                    slideInVertically { fullHeight -> fullHeight } + fadeIn() with
                            slideOutVertically { fullHeight -> -fullHeight } + fadeOut()
                }else{
                    slideInVertically { fullHeight -> -fullHeight } + fadeIn() with
                            slideOutVertically { fullHeight -> fullHeight } + fadeOut()
                }.using(
                    SizeTransform(clip = true)
                )
            }
        ) {newState->
            Text(
                text = newState.value.toString(),
                modifier = Modifier.width(30.dp),
                textAlign = TextAlign.Center
            )
        }
        IconButton(onClick = {
            if (value.value+1 in numberRange){
                value.value++
                onNumberChange(value.value)
            }
        }) {
            Icon(Icons.Filled.Add, contentDescription = null)
        }
    }
}