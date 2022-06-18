package com.kevin.rhodesislandassist.ui.component

import android.app.ActivityOptions
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NavigateBefore
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.kevin.rhodesislandassist.R
import com.kevin.rhodesislandassist.models.GameItem
import com.kevin.rhodesislandassist.ui.activity.DetailActivity
import com.kevin.rhodesislandassist.ui.activity.ui.theme.Dimension
import com.kevin.rhodesislandassist.ui.component.widget.DetailContent
import com.kevin.rhodesislandassist.ui.component.widget.ExpandableCard
import com.kevin.rhodesislandassist.ui.component.widget.ItemDetailCard
import com.kevin.rhodesislandassist.ui.viewmodel.DetailViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalUnitApi::class)
@Composable
fun ItemDetail(item: GameItem?,viewModel: DetailViewModel){
    if (item==null) return
    val context= LocalContext.current
    val refreshState = rememberSwipeRefreshState(isRefreshing = true)
    viewModel.getItemMatrix(item.itemId?:"",context,refreshState)
    Scaffold(
        topBar ={
            SmallTopAppBar(
                navigationIcon = { IconButton(onClick = { (context as DetailActivity).onBackPressed() }) {
                    Icon(Icons.Filled.NavigateBefore, contentDescription = null)
                } },
                title = { Text(text = item.name?:"") }
            )
        }
    ) {
        ElevatedCard(
                modifier = Modifier
                    .padding(it)
                    .padding(horizontal = 7.dp)
            ){
                SwipeRefresh(state = refreshState, onRefresh = { viewModel.refreshItem(item.itemId!!,context,refreshState) }) {
                    LazyColumn(modifier = Modifier.padding(top = 5.dp)){
                        items(viewModel.matrixes){matrix->
                            val stage=viewModel.getStageById(matrix.stageId)
                            if (stage!=null){
                                ItemDetailCard(stage = stage, matrix = matrix)
                            }
                        }
                    }
                }
            }
    }
}