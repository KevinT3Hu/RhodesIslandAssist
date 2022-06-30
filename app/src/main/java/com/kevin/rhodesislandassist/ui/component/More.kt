package com.kevin.rhodesislandassist.ui.component

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kevin.rhodesislandassist.R
import com.kevin.rhodesislandassist.ui.activity.*
import com.kevin.rhodesislandassist.ui.component.widget.ListGroup
import com.kevin.rhodesislandassist.ui.viewmodel.DataViewModel
import com.kevin.rhodesislandassist.util.getCurrentDate
import com.kevin.rhodesislandassist.util.json.PreferencesKeyRefresh

@Composable
fun More(viewModel: DataViewModel) {
    val context = LocalContext.current

    var showAccountsHintDialog by remember { mutableStateOf(false) }
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
        ),
        Action(
            title = R.string.title_gacha,
            icon = Icons.Filled.List,
            action = {
                showAccountsHintDialog = true
            }
        )
    )
    val about = mutableListOf(
        Action(
            title = R.string.title_refresh_tables,
            icon = Icons.Filled.Refresh,
            action = {
                if (context.getSharedPreferences(
                        context.resources.getString(R.string.settings_preferences),
                        Context.MODE_PRIVATE
                    ).getString(
                        PreferencesKeyRefresh, ""
                    ) == getCurrentDate()
                ) {
                    Toast.makeText(context, R.string.toast_refreshed_today, Toast.LENGTH_SHORT)
                        .show()
                } else {
                    viewModel.initDataSet(context, true)
                    Toast.makeText(context, R.string.toast_refresh, Toast.LENGTH_SHORT).show()
                }
            }
        ),
        Action(
            title = R.string.title_about,
            icon = Icons.Filled.Info,
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
            icon = Icons.Filled.DeveloperBoard,
            action = {
                context.startActivity(
                    Intent(
                        context, OpenSourceNoticesActivity::class.java
                    )
                )
            }
        )
    )
    if (showAccountsHintDialog) {
        AlertDialog(
            onDismissRequest = { showAccountsHintDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    context.startActivity(
                        Intent(context, GachaActivity::class.java)
                    )
                    showAccountsHintDialog = false
                }) {
                    Text(text = stringResource(id = R.string.title_dialog_continue))
                }
            },
            dismissButton = {
                TextButton(onClick = { showAccountsHintDialog = false }) {
                    Text(text = stringResource(id = R.string.title_dialog_cancel))
                }
            },
            text = {
                Text(text = stringResource(id = R.string.text_accounts_hint))
            }
        )
    }
    Column {
        ListGroup(
            data = tools,
            label = stringResource(id = R.string.title_toolbox),
            modifier = Modifier.padding(vertical = 5.dp)
        ) { tool ->
            ListItem(action = tool)
        }
        ListGroup(
            data = about,
            label = stringResource(id = R.string.title_others),
            modifier = Modifier.padding(vertical = 5.dp)
        ) { aboutItem ->
            ListItem(action = aboutItem)
        }
    }
}

private data class Action(
    @StringRes val title: Int,
    val icon: ImageVector? = null,
    val action: () -> Unit
)

@Composable
private fun ListItem(action: Action) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
        .fillMaxWidth()
        .height(55.dp)
        .clickable { action.action() }) {
        if (action.icon != null) {
            Box(
                modifier = Modifier
                    .padding(start = 20.dp, end = 15.dp)
                    .padding(vertical = 10.dp)
            ) {
                Icon(imageVector = action.icon, contentDescription = null)
            }
        }
        Text(
            text = stringResource(id = action.title),
            modifier = Modifier.padding(start = 10.dp)
        )
    }
}