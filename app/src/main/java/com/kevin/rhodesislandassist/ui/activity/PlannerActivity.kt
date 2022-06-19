package com.kevin.rhodesislandassist.ui.activity

import android.os.Bundle
import android.transition.Fade
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import com.kevin.rhodesislandassist.R
import com.kevin.rhodesislandassist.ui.theme.Dimension
import com.kevin.rhodesislandassist.ui.theme.RhodesIslandAssistTheme
import com.kevin.rhodesislandassist.ui.component.widget.ExpandableCard
import com.kevin.rhodesislandassist.ui.component.widget.NumberSelector
import com.kevin.rhodesislandassist.ui.component.widget.SearchDialog
import com.kevin.rhodesislandassist.ui.viewmodel.PlannerViewModel

class PlannerActivity : ComponentActivity() {
    @OptIn(
        ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(window) {
            requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)

            enterTransition = Fade(Fade.MODE_IN)
            exitTransition = Fade(Fade.MODE_OUT)
        }
        setContent {
            RhodesIslandAssistTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            CenterAlignedTopAppBar(title = { Text(text = stringResource(id = R.string.title_planner)) })
                        }
                    ) {
                        val viewModel: PlannerViewModel by viewModels()
                        Column(
                            modifier = Modifier.padding(it),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Card(modifier = Modifier.padding(horizontal = Dimension.HorizontalPadding)) {
                                val dialogShow = remember { mutableStateOf(false) }
                                Text(
                                    text = stringResource(id = R.string.hint_materials_required),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp)
                                )
                                LazyColumn(verticalArrangement = Arrangement.spacedBy(Dimension.ListItemPadding)) {
                                    items(viewModel.selectedRequiredMaterials.keys.toList()) { itemName ->
                                        val requiredNumber = remember {
                                            mutableStateOf(viewModel.selectedRequiredMaterials[itemName]!!)
                                        }
                                        Column(horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.SpaceAround,
                                            modifier = Modifier
                                                .padding(
                                                    vertical = Dimension.ListItemPadding,
                                                    horizontal = 5.dp
                                                )
                                                .clickable { }) {
                                            Row(
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                Text(text = itemName)
                                                NumberSelector(
                                                    value = requiredNumber,
                                                    onNumberChange = { value ->
                                                        viewModel.changeRequiredMaterialCount(
                                                            itemName,
                                                            value
                                                        )
                                                    })
                                            }
                                            Divider(
                                                color = Color.Gray,
                                                modifier = Modifier.height(0.3.dp)
                                            )
                                        }
                                    }
                                    item {
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable {
                                                    dialogShow.value = true
                                                }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.Add,
                                                contentDescription = null,
                                                modifier = Modifier.padding(vertical = 5.dp)
                                            )
                                        }
                                    }
                                }
                                SearchDialog(
                                    show = dialogShow,
                                    searchSource = viewModel.getShowUnselectedRequiredMaterials(),
                                    onConfirm = { reqItems ->
                                        reqItems.forEach { reqItem ->
                                            viewModel.addRequiredItem(reqItem)
                                        }
                                    }
                                )
                            }
                            Card(
                                modifier = Modifier
                                    .padding(top = 10.dp)
                                    .padding(horizontal = Dimension.HorizontalPadding)
                            ) {
                                val dialogShow = remember { mutableStateOf(false) }
                                Text(
                                    text = stringResource(id = R.string.hint_materials_owned),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .align(Alignment.CenterHorizontally)
                                        .padding(10.dp)
                                )
                                LazyColumn(verticalArrangement = Arrangement.spacedBy(Dimension.ListItemPadding)) {
                                    items(viewModel.selectedOwnedMaterials.keys.toList()) { itemName ->
                                        val ownedNumber = remember {
                                            mutableStateOf(viewModel.selectedOwnedMaterials[itemName]!!)
                                        }
                                        Column(horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.SpaceAround,
                                            modifier = Modifier
                                                .padding(
                                                    vertical = Dimension.ListItemPadding,
                                                    horizontal = 5.dp
                                                )
                                                .clickable { }) {
                                            Row(
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                Text(text = itemName)
                                                NumberSelector(
                                                    value = ownedNumber,
                                                    onNumberChange = { value ->
                                                        viewModel.changeOwnedMaterialCount(
                                                            itemName,
                                                            value
                                                        )
                                                    })
                                            }
                                            Divider(
                                                color = Color.Gray,
                                                modifier = Modifier.height(0.3.dp)
                                            )

                                        }
                                    }
                                    item {
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable {
                                                    dialogShow.value = true
                                                }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.Add,
                                                contentDescription = null,
                                                modifier = Modifier.padding(vertical = 5.dp)
                                            )
                                        }
                                    }
                                }
                                SearchDialog(
                                    show = dialogShow,
                                    searchSource = viewModel.getShowUnselectedOwnedMaterials(),
                                    onConfirm = { reqItems ->
                                        reqItems.forEach { ownedItem ->
                                            viewModel.addOwnedItem(ownedItem)
                                        }
                                    }
                                )
                            }
                            ElevatedButton(
                                onClick = {
                                    viewModel.getPlan()
                                },
                                modifier = Modifier.padding(vertical = 5.dp)
                            ) {
                                Icon(Icons.Filled.Timeline, contentDescription = null)
                                Text(text = stringResource(id = R.string.hint_plan))
                            }
                            AnimatedContent(targetState = viewModel.planStatus) { status ->
                                if (status.value) {
                                    Column {
                                        Card(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(
                                                    horizontal = Dimension.HorizontalPadding,
                                                    vertical = Dimension.ListItemPadding
                                                )
                                        ) {
                                            val plan = viewModel.plan.value!!
                                            Text(
                                                text = "${stringResource(id = R.string.hint_expected_cost)}:${plan.cost}",
                                                modifier = Modifier
                                                    .padding(5.dp)
                                                    .padding(top = 5.dp)
                                            )
                                            Text(
                                                text = "${stringResource(id = R.string.hint_expected_gcost)}:${plan.gcost}",
                                                modifier = Modifier.padding(5.dp)
                                            )
                                            Divider(color = Color.Gray,modifier = Modifier
                                                .padding(5.dp)
                                                .fillMaxWidth())
                                            LazyColumn(modifier = Modifier.padding(horizontal = 5.dp)) {
                                                items(plan.stages) { stage ->
                                                    ExpandableCard(
                                                        cardContent = {
                                                            Row(
                                                                verticalAlignment = Alignment.CenterVertically,
                                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                                modifier = Modifier
                                                                    .padding(5.dp)
                                                                    .padding(vertical = 7.dp)
                                                                    .fillMaxWidth()
                                                            ) {
                                                                Text(text = stage.stage)
                                                                Text(text = "${stage.count}")
                                                            }
                                                        },
                                                        cardExpandedContent = {
                                                            stage.items.forEach { item ->
                                                                Column(modifier = Modifier.fillMaxWidth()) {
                                                                    Row(
                                                                        verticalAlignment = Alignment.CenterVertically,
                                                                        horizontalArrangement = Arrangement.SpaceBetween
                                                                    ) {
                                                                        Text(text = item.key)
                                                                        Text(text = "${item.value}")
                                                                    }
                                                                    Divider(
                                                                        modifier = Modifier.padding(
                                                                            horizontal = Dimension.HorizontalPadding
                                                                        ), color = Color.Gray
                                                                    )
                                                                }
                                                            }
                                                        },
                                                        modifier = Modifier.fillMaxWidth()
                                                    )
                                                }
                                            }
                                        }
                                        Card(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(
                                                    horizontal = Dimension.HorizontalPadding,
                                                    vertical = Dimension.ListItemPadding
                                                )
                                        ) {
                                            Text(text = stringResource(id = R.string.hint_synthesis_target), modifier = Modifier
                                                .padding(5.dp)
                                                .padding(top = 5.dp))
                                            Divider(color = Color.Gray, modifier = Modifier
                                                .padding(5.dp)
                                                .fillMaxWidth())
                                            LazyColumn(modifier = Modifier.padding(5.dp)) {
                                                items(viewModel.plan.value!!.syntheses) { synthesis ->
                                                    Text(text = synthesis.target, modifier = Modifier.padding(5.dp))
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
