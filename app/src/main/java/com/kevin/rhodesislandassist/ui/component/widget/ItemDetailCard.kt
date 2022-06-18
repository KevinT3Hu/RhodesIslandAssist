package com.kevin.rhodesislandassist.ui.component.widget

import android.app.ActivityOptions
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.kevin.rhodesislandassist.R
import com.kevin.rhodesislandassist.api.models.Matrix
import com.kevin.rhodesislandassist.models.GameStage
import com.kevin.rhodesislandassist.ui.activity.DetailActivity
import com.kevin.rhodesislandassist.ui.activity.ui.theme.Dimension

@OptIn(ExperimentalUnitApi::class)
@Composable
fun ItemDetailCard(stage:GameStage,matrix: Matrix,modifier: Modifier=Modifier){
    val context= LocalContext.current
    ExpandableCard(
        cardContent = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "${stage.code}-${stage.name}", fontSize = TextUnit(20f, TextUnitType.Sp))
            }
        },
        cardExpandedContent = {
            Column(modifier = Modifier.fillMaxWidth()) {

            }
            DetailContent(matrix = matrix, stage = stage)
            Divider(modifier = Modifier
                .padding(horizontal = 3.dp)
                .padding(top = 5.dp)
                .fillMaxWidth(),color= Color.Gray)
            Column(modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    context.startActivity(
                        Intent(context, DetailActivity::class.java)
                            .putExtra(
                                DetailActivity.ExtraTagType,
                                DetailActivity.TypeStage
                            )
                            .putExtra(
                                DetailActivity.ExtraDataItemOrStage,
                                stage
                            ),
                        ActivityOptions
                            .makeSceneTransitionAnimation(context as DetailActivity)
                            .toBundle()
                    )
                }, verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = stringResource(id = R.string.hint_stage_detail), color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(vertical = 12.dp))
            }
        },
        modifier = modifier
            .padding(Dimension.ListItemPadding)
    )
}