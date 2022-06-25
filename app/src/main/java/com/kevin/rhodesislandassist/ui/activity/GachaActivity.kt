package com.kevin.rhodesislandassist.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FiberNew
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kevin.rhodesislandassist.R
import com.kevin.rhodesislandassist.ui.activity.ui.theme.RhodesIslandAssistTheme
import com.kevin.rhodesislandassist.ui.component.LoginWeb
import com.kevin.rhodesislandassist.ui.component.widget.PieChart
import com.kevin.rhodesislandassist.ui.theme.Dimension
import com.kevin.rhodesislandassist.ui.theme.DividerColor
import com.kevin.rhodesislandassist.ui.viewmodel.GachaViewModel

class GachaActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RhodesIslandAssistTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: GachaViewModel by viewModels()
                    val loginStatus = viewModel.loginStatus
                    val token = viewModel.token
                    if (loginStatus.value) {
                        viewModel.fetchCards(this@GachaActivity)
                        Scaffold(
                            topBar = {
                                var dropDownMenuExpanded by remember { mutableStateOf(false) }
                                CenterAlignedTopAppBar(
                                    title = { Text(text = stringResource(id = R.string.title_gacha)) },
                                    actions = {
                                        IconButton(onClick = {
                                            dropDownMenuExpanded = !dropDownMenuExpanded
                                        }) {
                                            Icon(Icons.Filled.FilterList, contentDescription = null)
                                        }
                                        DropdownMenu(
                                            expanded = dropDownMenuExpanded,
                                            onDismissRequest = {
                                                dropDownMenuExpanded = !dropDownMenuExpanded
                                            }) {
                                            viewModel.pools.forEach { pool ->
                                                DropdownMenuItem(
                                                    text = { Text(text = pool) },
                                                    onClick = {
                                                        viewModel.filterByPool(pool)
                                                        dropDownMenuExpanded = !dropDownMenuExpanded
                                                    })
                                            }
                                            if (viewModel.isFiltered.value) {
                                                DropdownMenuItem(
                                                    text = { Text(text = stringResource(id = R.string.title_clear_filter)) },
                                                    onClick = {
                                                        viewModel.clearFilter()
                                                        dropDownMenuExpanded = !dropDownMenuExpanded
                                                    })
                                            }
                                        }
                                    }
                                )
                            }
                        ) {
                            LazyColumn(
                                modifier = Modifier
                                    .padding(it)
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                item {
                                    var showRarityRate by rememberSaveable { mutableStateOf(false) }
                                    var raritySelected by rememberSaveable { mutableStateOf(0) }
                                    PieChart(
                                        pieSlices = viewModel.pieSlicesFiltered,
                                        modifier = Modifier.padding(10.dp),
                                        onSliceClick = { slice ->
                                            if (showRarityRate && slice.name == "$raritySelected") {
                                                showRarityRate = false
                                            } else {
                                                showRarityRate = true
                                                raritySelected = slice.name.toInt()
                                            }
                                        }
                                    )
                                    AnimatedVisibility(visible = showRarityRate) {
                                        Card(
                                            modifier = Modifier
                                                .padding(horizontal = Dimension.HorizontalPadding)
                                                .fillMaxWidth()
                                                .clickable { }
                                        ) {
                                            Row(
                                                horizontalArrangement = Arrangement.Center,
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                Text(
                                                    text = "${
                                                        stringResource(
                                                            id = R.string.hint_rarity_rate,
                                                            raritySelected + 1
                                                        )
                                                    }:${viewModel.getRarityRate(raritySelected)}%",
                                                    modifier = Modifier.padding(vertical = 15.dp)
                                                )
                                            }
                                        }
                                    }
                                    Divider(color = DividerColor)
                                }
                                items(viewModel.cardsFiltered) { card ->
                                    Column(modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { }) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = card.name, modifier = Modifier
                                                    .padding(start = 20.dp)
                                                    .padding(vertical = 12.dp),
                                                color = viewModel.getRarityColor(card.rarity)
                                            )
                                            if (card.isNew) {
                                                Icon(
                                                    Icons.Filled.FiberNew,
                                                    contentDescription = null,
                                                    modifier = Modifier.padding(start = 10.dp)
                                                )
                                            }
                                        }
                                        Divider(color = DividerColor)
                                    }
                                }
                            }
                        }
                    } else {
                        LoginWeb(loginStatus = loginStatus, token = token)
                    }
                }
            }
        }
    }
}