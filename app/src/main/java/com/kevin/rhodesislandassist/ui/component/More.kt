package com.kevin.rhodesislandassist.ui.component

import android.app.ActivityOptions
import android.content.Intent
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kevin.rhodesislandassist.R
import com.kevin.rhodesislandassist.ui.activity.AboutActivity
import com.kevin.rhodesislandassist.ui.activity.MainActivity
import com.kevin.rhodesislandassist.ui.activity.OpenSourceNoticesActivity
import com.kevin.rhodesislandassist.ui.activity.PlannerActivity
import com.kevin.rhodesislandassist.ui.component.widget.ListGroup

@Composable
fun More() {
    val context = LocalContext.current
    val tools = mutableListOf(
        Action(
            title = R.string.title_planner,
            icon = Icons.Filled.Timeline,
            action = {
                context.startActivity(
                    Intent(context, PlannerActivity::class.java),
                    ActivityOptions.makeSceneTransitionAnimation(context as MainActivity).toBundle()
                )
            }
        )
    )
    val about = mutableListOf(
        Action(
            title = R.string.title_about,
            action = {
                context.startActivity(
                    Intent(
                        context, AboutActivity::class.java
                    )
                )
            }
        ),
        Action(
            title = R.string.title_license,
            action = {
                context.startActivity(
                    Intent(
                        context, OpenSourceNoticesActivity::class.java
                    )
                )
            }
        )
    )
    Column {
        ListGroup(
            data = tools,
            label = stringResource(id = R.string.title_toolbox),
            modifier = Modifier.padding(vertical = 5.dp)
        ) { tool ->
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .clickable { tool.action() }) {
                Box(
                    modifier = Modifier
                        .padding(start = 20.dp, end = 15.dp)
                        .padding(vertical = 10.dp)
                ) {
                    Icon(imageVector = tool.icon!!, contentDescription = null)
                }
                Text(
                    text = stringResource(id = tool.title),
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        }
        ListGroup(
            data = about,
            label = stringResource(id = R.string.title_others),
            modifier = Modifier.padding(vertical = 5.dp)
        ) { aboutItem ->
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .clickable { aboutItem.action() }
            ) {
                Text(
                    text = stringResource(id = aboutItem.title),
                    modifier = Modifier.padding(start = 30.dp)
                )
            }
        }
    }
}

private data class Action(
    @StringRes val title: Int,
    val icon: ImageVector? = null,
    val action: () -> Unit
)