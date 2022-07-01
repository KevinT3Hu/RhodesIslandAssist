package com.kevin.rhodesislandassist.ui.component

import android.app.ActivityOptions
import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.scale
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
import com.kevin.rhodesislandassist.ui.theme.Dimension
import com.kevin.rhodesislandassist.ui.theme.DividerColor
import com.kevin.rhodesislandassist.ui.theme.ReverseColor
import com.kevin.rhodesislandassist.ui.theme.rarityColor
import com.kevin.rhodesislandassist.ui.viewmodel.DataViewModel

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalUnitApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun Search(viewModel: DataViewModel) {
    val context = LocalContext.current
    viewModel.fetchDataFromSearchText(context)
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Surface(
                color = MaterialTheme.colorScheme.background,
                shadowElevation = 4.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painterResource(id = R.drawable.ic_logo),
                    contentDescription = null,
                    modifier = Modifier.padding(bottom = 20.dp)
                )
            }
        }
        stickyHeader {
            Surface(color = MaterialTheme.colorScheme.background, shadowElevation = 2.dp) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = viewModel.searchText,
                        onValueChange = {
                            viewModel.searchText = it
                            viewModel.fetchDataFromSearchText(context)
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
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(modifier = Modifier.weight(1.2f)) {
                            ElevatedFilterChip(
                                selected = viewModel.itemChipSelected,
                                onClick = {
                                    viewModel.itemChipSelected = !viewModel.itemChipSelected
                                    viewModel.fetchDataFromSearchText(context)
                                },
                                label = {
                                    Text(text = stringResource(id = R.string.chip_item))
                                },
                                selectedIcon = {
                                    Icon(Icons.Filled.Done, contentDescription = null)
                                },
                                modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                            )
                            ElevatedFilterChip(
                                selected = viewModel.stageChipSelected,
                                onClick = {
                                    viewModel.stageChipSelected = !viewModel.stageChipSelected
                                    viewModel.fetchDataFromSearchText(context)
                                },
                                label = {
                                    Text(text = stringResource(id = R.string.chip_stage))
                                },
                                selectedIcon = {
                                    Icon(Icons.Filled.Done, contentDescription = null)
                                },
                                modifier = Modifier.padding(end = 10.dp)
                            )
                            ElevatedFilterChip(
                                selected = viewModel.characterChipSelected,
                                onClick = {
                                    viewModel.characterChipSelected =
                                        !viewModel.characterChipSelected
                                    viewModel.fetchDataFromSearchText(context)
                                },
                                label = {
                                    Text(text = stringResource(id = R.string.chip_char))
                                },
                                selectedIcon = {
                                    Icon(imageVector = Icons.Filled.Done, contentDescription = null)
                                }
                            )
                        }

                        Text(
                            text = "${viewModel.getTotalNumberOfSearchedItems()}",
                            color = Color.Gray,
                            modifier = Modifier
                                .weight(0.2f)
                                .padding(end = 5.dp)
                        )
                    }
                }
            }
        }
        items(viewModel.gameItemSearchResultDataSet) { item ->
            Column(modifier = Modifier.clickable {
                context.startActivity(
                    Intent(context, DetailActivity::class.java)
                        .putExtra(
                            DetailActivity.ExtraTagType,
                            DetailActivity.TypeItem
                        )
                        .putExtra(DetailActivity.ExtraData, item),
                    ActivityOptions
                        .makeSceneTransitionAnimation(context as MainActivity)
                        .toBundle()
                )
            }) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .fillMaxSize()
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
                            text = item.name ?: "",
                            fontSize = TextUnit(20f, TextUnitType.Sp),
                            modifier = Modifier.padding(vertical = 7.dp)
                        )
                    }
                }
                Divider(
                    color = DividerColor,
                    modifier = Modifier.padding(horizontal = Dimension.HorizontalPadding)
                )
            }

        }
            items(viewModel.gameStageSearchResultDataSet) { stage ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            context.startActivity(
                                Intent(context, DetailActivity::class.java)
                                    .putExtra(DetailActivity.ExtraTagType, DetailActivity.TypeStage)
                                    .putExtra(DetailActivity.ExtraData, stage)
                            )
                        }) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                            .fillMaxSize()
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
                    Divider(
                        color = DividerColor,
                        modifier = Modifier.padding(horizontal = Dimension.HorizontalPadding)
                    )
                }
            }
        items(viewModel.characterSearchResultDataSet) { character ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        context.startActivity(
                            Intent(context, DetailActivity::class.java)
                                .putExtra(DetailActivity.ExtraTagType, DetailActivity.TypeChar)
                                .putExtra(DetailActivity.ExtraData, character)
                        )
                    }
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .fillMaxSize()
                ) {
                    Image(
                        painterResource(id = character.profession.getDrawableIcon()),
                        contentDescription = null,
                        colorFilter = if (!isSystemInDarkTheme()) ColorFilter.colorMatrix(
                            ReverseColor
                        ) else null,
                        modifier = Modifier
                            .scale(4f)
                            .clickable {
                                viewModel.searchText =
                                    "prof:${context.resources.getString(character.profession.getProfessionName())}"
                            }
                    )
                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.fillMaxWidth(0.6f)
                    ) {
                        Text(
                            text = character.name,
                            color = rarityColor(character.rarity, isSystemInDarkTheme()),
                            fontSize = TextUnit(20f, TextUnitType.Sp),
                            modifier = Modifier.padding(top = 5.dp)
                        )
                        Row {
                            character.tagList.forEach {
                                AssistChip(
                                    onClick = { viewModel.searchText = "tag:$it" },
                                    label = {
                                        Text(
                                            text = it,
                                            fontSize = TextUnit(12f, TextUnitType.Sp)
                                        )
                                    },
                                    modifier = Modifier.padding(end = 3.dp),
                                )
                            }
                        }
                    }
                }
                Divider(
                    color = DividerColor,
                    modifier = Modifier.padding(horizontal = Dimension.HorizontalPadding)
                )
            }
        }
    }
}
