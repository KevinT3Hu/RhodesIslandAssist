package com.kevin.rhodesislandassist.ui.component

import android.app.ActivityOptions
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.kevin.rhodesislandassist.R
import com.kevin.rhodesislandassist.models.StageType
import com.kevin.rhodesislandassist.ui.activity.DetailActivity
import com.kevin.rhodesislandassist.ui.activity.MainActivity
import com.kevin.rhodesislandassist.ui.viewmodel.DataViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalUnitApi::class)
@Composable
fun Search(viewModel: DataViewModel) {
    viewModel.fetchDataFromSearchText()
    val context= LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painterResource(id = R.drawable.ic_logo),
            contentDescription = null,
            modifier = Modifier.padding(bottom = 20.dp)
        )
        OutlinedTextField(
            value = viewModel.searchText,
            onValueChange = {
                viewModel.searchText = it
                viewModel.fetchDataFromSearchText()
            },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
            trailingIcon = {
                IconButton(onClick = {
                    viewModel.searchText = ""
                }) {
                    Icon(Icons.Filled.Clear, contentDescription = null)
                }
            },
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        )
        Row(horizontalArrangement = Arrangement.Start) {
            ElevatedFilterChip(
                selected = viewModel.itemChipSelected,
                onClick = {
                    viewModel.itemChipSelected = !viewModel.itemChipSelected
                          viewModel.fetchDataFromSearchText()
                          },
                label = {
                    Text(text = stringResource(id = R.string.chip_item))
                },
                selectedIcon = {
                    Icon(Icons.Filled.Done, contentDescription = null)
                },
                modifier = Modifier.padding(end = 10.dp)
            )
            ElevatedFilterChip(
                selected = viewModel.stageChipSelected,
                onClick = {
                    viewModel.stageChipSelected = !viewModel.stageChipSelected
                          viewModel.fetchDataFromSearchText()
                          },
                label = {
                    Text(text = stringResource(id = R.string.chip_stage))
                },
                selectedIcon = {
                    Icon(Icons.Filled.Done, contentDescription = null)
                }
            )
        }
        LazyColumn {
            items(viewModel.gameItemSearchResultDataSet) { item ->
                ElevatedCard(
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                        .fillMaxWidth(),
                    onClick = {
                              context.startActivity(
                                  Intent(context,DetailActivity::class.java)
                                      .putExtra(DetailActivity.ExtraTagType,DetailActivity.TypeItem)
                                      .putExtra(DetailActivity.ExtraDataItemOrStage,item),
                                  ActivityOptions.makeSceneTransitionAnimation(context as MainActivity).toBundle()
                              )
                    },
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 10.dp)
                    ) {
                        Image(
                            Icons.Filled.Category,
                            contentDescription = null,
                            colorFilter = if (isSystemInDarkTheme()) {
                                ColorFilter.tint(Color.White)
                            } else {
                                null
                            }
                        )
                        Column(
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier.fillMaxWidth(0.6f)
                        ) {
                            Text(
                                text = item.name?:"",
                                fontSize = TextUnit(20f, TextUnitType.Sp),
                                modifier = Modifier.padding(vertical = 7.dp)
                            )
                            Text(text = item.description?:"", fontSize = TextUnit(12f, TextUnitType.Sp))
                        }
                    }
                }
            }
            items(viewModel.gameStageSearchResultDataSet) { stage ->
                ElevatedCard(
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                        .fillMaxWidth(),
                    onClick = {
                        context.startActivity(
                            Intent(context,DetailActivity::class.java)
                                .putExtra(DetailActivity.ExtraTagType,DetailActivity.TypeStage)
                                .putExtra(DetailActivity.ExtraDataItemOrStage,stage)
                        )
                    }) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 10.dp)
                    ) {
                        Image(
                            Icons.Filled.Games,
                            contentDescription = null,
                            colorFilter = if (isSystemInDarkTheme()) {
                                ColorFilter.tint(Color.White)
                            } else
                                null
                        )
                        Column(
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier.fillMaxWidth(0.6f)
                        ) {
                            Text(text = stringResource(id = StageType.getLabelId(stage.type)))
                            Text(
                                text = "${stage.code}-${stage.name}", fontSize = TextUnit(
                                    20f,
                                    TextUnitType.Sp
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}