package com.kevin.rhodesislandassist.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kevin.rhodesislandassist.R
import com.kevin.rhodesislandassist.ui.activity.ui.theme.RhodesIslandAssistTheme
import com.kevin.rhodesislandassist.ui.component.LoginWeb
import com.kevin.rhodesislandassist.ui.component.widget.PieChart
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
                                CenterAlignedTopAppBar(
                                    title = { Text(text = stringResource(id = R.string.title_gacha)) }
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
                                    PieChart(
                                        pieSlices = viewModel.pieSlices,
                                        modifier = Modifier.padding(10.dp),
                                        onSliceClick = { slice ->
                                            Log.i("gacha", slice.name)
                                        })
                                    Divider(color = DividerColor)
                                }
                                items(viewModel.cards) { card ->
                                    Column(modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { }) {
                                        Text(text = card.name, modifier = Modifier
                                            .padding(start = 20.dp)
                                            .padding(vertical = 12.dp),
                                            color = viewModel.getRarityColor(card.rarity)
                                        )
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