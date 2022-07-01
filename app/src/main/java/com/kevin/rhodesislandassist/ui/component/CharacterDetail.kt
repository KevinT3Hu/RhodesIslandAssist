package com.kevin.rhodesislandassist.ui.component

import androidx.annotation.StringRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NavigateBefore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kevin.rhodesislandassist.R
import com.kevin.rhodesislandassist.models.Character
import com.kevin.rhodesislandassist.ui.activity.DetailActivity
import com.kevin.rhodesislandassist.ui.theme.Dimension
import com.kevin.rhodesislandassist.ui.theme.DividerColor
import com.kevin.rhodesislandassist.ui.theme.ReverseColor
import com.kevin.rhodesislandassist.ui.viewmodel.DetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetail(character: Character?, viewModel: DetailViewModel) {
    if (character == null) return
    val context = LocalContext.current
    viewModel.initCharacterAttr(character)
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = character.name) },
                navigationIcon = {
                    IconButton(onClick = { (context as DetailActivity).onBackPressed() }) {
                        Icon(Icons.Filled.NavigateBefore, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = Dimension.HorizontalPadding)
                    .padding(bottom = Dimension.ListItemPadding)
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(
                        horizontal = Dimension.HorizontalPadding,
                        vertical = 20.dp
                    )
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(end = 10.dp)
                    ) {
                        Image(
                            painterResource(id = character.profession.getDrawableIcon()),
                            contentDescription = null,
                            colorFilter = if (!isSystemInDarkTheme()) {
                                ColorFilter.colorMatrix(ReverseColor)
                            } else null,
                            modifier = Modifier.size(50.dp)
                        )
                        Text(text = "${stringResource(id = character.profession.getProfessionName())}-${character.subProfessionalId}")
                    }
                    Text(text = character.itemUsage)
                }
                Text(
                    text = character.desc, modifier = Modifier
                        .padding(horizontal = Dimension.HorizontalPadding)
                        .padding(bottom = 10.dp)
                )
            }
            CardWithSpecs {
                Row(modifier = Modifier.padding(vertical = 15.dp)) {
                    AttrColumn(
                        attrName = R.string.title_cost,
                        value = viewModel.attr.cost,
                        weight = 0.33f
                    )
                    AttrColumn(
                        attrName = R.string.title_respawn_time,
                        value = viewModel.attr.respawnTime,
                        weight = 0.33f
                    )
                    AttrColumn(
                        attrName = R.string.title_atk_speed,
                        value = viewModel.attr.attackSpeed,
                        weight = 0.33f
                    )
                }
            }
            CardWithSpecs {
                var levelFloat by remember { mutableStateOf(1f) }
                Text(
                    text = "${stringResource(id = R.string.text_phase)}${
                        viewModel.getPhaseAndLevel(
                            character,
                            levelFloat.toInt()
                        ).first
                    } - ${viewModel.getPhaseAndLevel(character, levelFloat.toInt()).second}${
                        stringResource(
                            id = R.string.text_level
                        )
                    }", textAlign = TextAlign.Center, modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                )
                Row(
                    modifier = Modifier
                        .padding(horizontal = Dimension.HorizontalPadding)
                        .fillMaxWidth()
                ) {
                    AttrColumn(
                        attrName = R.string.title_max_hp,
                        value = viewModel.attr.maxHp,
                        weight = 0.25f
                    )
                    AttrColumn(
                        attrName = R.string.title_atk,
                        value = viewModel.attr.attack,
                        weight = 0.25f
                    )
                    AttrColumn(
                        attrName = R.string.title_def,
                        value = viewModel.attr.defense,
                        weight = 0.25f
                    )
                    AttrColumn(
                        attrName = R.string.title_magic_resistance,
                        value = viewModel.attr.magicResistance,
                        weight = 0.25f
                    )
                }
                Slider(
                    value = levelFloat, onValueChange = {
                        levelFloat = it
                        viewModel.updateCurrentAttribute(character, it.toInt())
                    },
                    valueRange = 1f..character.getTotalLevel().toFloat(),
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CardWithSpecs(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    ElevatedCard(modifier = modifier
        .padding(horizontal = Dimension.HorizontalPadding, vertical = Dimension.ListItemPadding)
        .fillMaxWidth()
        .clickable { }
    ) {
        content()
    }
}

@Composable
private fun ListDivider() {
    Divider(
        color = DividerColor,
        modifier = Modifier.padding(horizontal = Dimension.HorizontalPadding, vertical = 5.dp)
    )
}

@Composable
private fun RowScope.AttrColumn(@StringRes attrName: Int, value: Int, weight: Float) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(weight)) {
        Text(text = stringResource(id = attrName))
        ListDivider()
        Text(text = "$value")
    }
}