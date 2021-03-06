package com.kevin.rhodesislandassist.ui.component

import android.app.ActivityOptions
import android.content.Intent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NavigateBefore
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.kevin.rhodesislandassist.R
import com.kevin.rhodesislandassist.models.GameStage
import com.kevin.rhodesislandassist.ui.activity.DetailActivity
import com.kevin.rhodesislandassist.ui.activity.UploadActivity
import com.kevin.rhodesislandassist.ui.component.widget.StageDetailCard
import com.kevin.rhodesislandassist.ui.viewmodel.DetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StageDetail(stage: GameStage?, viewModel: DetailViewModel) {
    if (stage == null) return
    //stage nonnull
    val context = LocalContext.current
    val refreshState = rememberSwipeRefreshState(isRefreshing = true)
    viewModel.getStageMatrix(stage.stageId ?: "", context, refreshState)
    Scaffold(
        topBar = {
            SmallTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { (context as DetailActivity).onBackPressed() }) {
                        Icon(Icons.Filled.NavigateBefore, contentDescription = null)
                    }
                },
                title = { Text(text = "${stage.code}-${stage.name}") },
                actions = {
                    //action for uploading drops
                    IconButton(onClick = {
                        context.startActivity(
                            Intent(context, UploadActivity::class.java)
                                .putExtra(UploadActivity.ExtraStage, stage),
                            ActivityOptions.makeSceneTransitionAnimation(context as DetailActivity)
                                .toBundle()
                        )
                        //Toast.makeText(context, R.string.toast_unimplemented, Toast.LENGTH_SHORT)
                        //    .show()
                    }) {
                        Icon(
                            Icons.Filled.Upload,
                            contentDescription = stringResource(id = R.string.page_upload)
                        )
                    }
                }
            )
        }
    ) {
        ElevatedCard(
            modifier = Modifier
                .padding(it)
                .padding(7.dp)
        ) {
            SwipeRefresh(
                state = refreshState,
                onRefresh = { viewModel.refreshStage(stage.stageId!!, context, refreshState) }) {
                LazyColumn(modifier = Modifier.padding(top = 5.dp)) {
                    item {
                        Text(text = stage.desc!!, modifier = Modifier.padding(10.dp))
                    }
                    item {
                        Text(
                            text = "${stringResource(id = R.string.hint_ap_cost)}???${stage.apCost}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp)
                                .padding(bottom = 10.dp)
                        )
                    }
                    items(viewModel.matrixes) { matrix ->
                        val item = viewModel.getItemById(matrix.itemId)
                        if (item != null) {
                            StageDetailCard(item = item, stage = stage, matrix = matrix)
                        }
                    }
                }
            }
        }
    }
}