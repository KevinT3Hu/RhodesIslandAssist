package com.kevin.rhodesislandassist.ui.activity

import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kevin.rhodesislandassist.R
import com.kevin.rhodesislandassist.models.GameStage
import com.kevin.rhodesislandassist.ui.component.widget.NumberSelector
import com.kevin.rhodesislandassist.ui.theme.Dimension
import com.kevin.rhodesislandassist.ui.theme.RhodesIslandAssistTheme
import com.kevin.rhodesislandassist.ui.viewmodel.UploadViewModel

class UploadActivity : ComponentActivity() {

    companion object {
        const val ExtraStage = "STAGE"
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(window) {
            requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)

            enterTransition = Slide(Gravity.END)
        }
        val stage = intent.getParcelableExtra<GameStage>(ExtraStage)!!
        setContent {
            RhodesIslandAssistTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: UploadViewModel by viewModels()
                    val snackbarHostState = remember { SnackbarHostState() }
                    Scaffold(
                        topBar = {
                            SmallTopAppBar(
                                navigationIcon = {
                                    IconButton(onClick = { finish() }) {
                                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                                    }
                                },
                                title = { Text(text = "${stringResource(id = R.string.title_activity_upload)}:${stage.code}-${stage.name}") }
                            )
                        },
                        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                        floatingActionButton = {
                            FloatingActionButton(onClick = {
                                viewModel.report(
                                    this@UploadActivity,
                                    stage.stageId!!,
                                    snackbarHostState
                                )
                            }) {
                                Icon(Icons.Filled.Upload, contentDescription = null)
                            }
                        }
                    ) { padding ->
                        Column(
                            modifier = Modifier
                                .padding(padding)
                                .padding(horizontal = Dimension.HorizontalPadding)
                                .verticalScroll(rememberScrollState())
                        ) {
                            var commonDrops: List<GameStage.DropInfo>
                            var extraDrops = listOf<GameStage.DropInfo>()
                            stage.stageDrops!!.partition {
                                it.dropType == GameStage.DropType.COMMON
                            }.apply {
                                commonDrops = first
                                extraDrops = second.partition {
                                    it.dropType != GameStage.DropType.FIRST_TIME
                                }.first
                            }

                            Text(
                                text = stringResource(id = R.string.hint_common_drop),
                                modifier = Modifier
                                    .padding(5.dp)
                                    .padding(top = 5.dp)
                            )
                            Card(modifier = Modifier.fillMaxWidth()) {
                                DropColumn(items = commonDrops, viewModel = viewModel)
                            }
                            Text(
                                text = stringResource(id = R.string.hint_extra_drop),
                                modifier = Modifier
                                    .padding(5.dp)
                                    .padding(top = 5.dp)
                            )
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp)
                            ) {
                                DropColumn(items = extraDrops, viewModel = viewModel)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DropColumn(items: List<GameStage.DropInfo>, viewModel: UploadViewModel) {
    Column {
        items.forEach { dropInfo ->
            val item = viewModel.getItemById(dropInfo.id!!)
            if (item != null) {
                val count = remember { mutableStateOf(0) }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {}) {
                    Text(text = item.name!!, modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp))
                    NumberSelector(value = count, onNumberChange = { newCount ->
                        if (newCount == 0) {
                            viewModel.removeDrop(getDropType(dropInfo.dropType), dropInfo.id)
                        } else {
                            viewModel.updateDrop(
                                getDropType(dropInfo.dropType),
                                dropInfo.id,
                                newCount
                            )
                        }
                    })
                }
            }
        }
    }
}

private fun getDropType(dropType: GameStage.DropType): String {
    if (dropType == GameStage.DropType.COMMON) return "NORMAL_DROP"
    if (dropType == GameStage.DropType.SMALL_CHANCE || dropType == GameStage.DropType.MINOR_CHANCE) return "EXTRA_DROP"
    return ""
}