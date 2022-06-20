package com.kevin.rhodesislandassist.ui.component.widget

import android.icu.text.DateFormat
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kevin.rhodesislandassist.R
import com.kevin.rhodesislandassist.api.models.Matrix
import com.kevin.rhodesislandassist.models.GameStage

@Composable
fun DetailContent(matrix: Matrix, stage: GameStage) {
    val dataToShow = mapOf(
        Pair(R.string.label_drop_count, matrix.quantity.toString()),
        Pair(R.string.label_sample_count, matrix.times.toString()),
        Pair(R.string.label_drop_rate, matrix.dropDate().toString()),
        Pair(
            R.string.label_single_expected_ap_cost,
            matrix.expectedApCostPerItem(stage.apCost).toString()
        ),
        Pair(
            R.string.label_data_time_range, "${
                DateFormat.getDateInstance(
                    DateFormat.MEDIUM
                ).format(matrix.getStartDate())
            }-${DateFormat.getDateInstance(DateFormat.MEDIUM).format(matrix.getEndDate())}"
        )
    )
    Column {
        dataToShow.forEach { data ->
            TextWithLabel(
                label = stringResource(id = data.key),
                text = data.value,
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp)
            )
        }
    }
}