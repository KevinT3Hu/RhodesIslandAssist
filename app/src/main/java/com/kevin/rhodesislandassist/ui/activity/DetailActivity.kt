package com.kevin.rhodesislandassist.ui.activity

import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.kevin.rhodesislandassist.models.GameItem
import com.kevin.rhodesislandassist.models.GameStage
import com.kevin.rhodesislandassist.ui.activity.ui.theme.RhodesIslandAssistTheme
import com.kevin.rhodesislandassist.ui.component.ItemDetail
import com.kevin.rhodesislandassist.ui.component.StageDetail
import com.kevin.rhodesislandassist.ui.viewmodel.DetailViewModel

class DetailActivity : ComponentActivity() {

    companion object{
        const val ExtraTagType="TYPE"
        const val TypeItem=0
        const val TypeStage=1
        const val TypeError=-1
        const val ExtraDataItemOrStage="DATA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(window){
            requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)

            enterTransition=Slide(Gravity.END)
            exitTransition=Slide(Gravity.START)
        }
        setContent {
            RhodesIslandAssistTheme {
                val viewModel:DetailViewModel by viewModels()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val type=intent.getIntExtra(ExtraTagType, TypeError)
                    if (type== TypeError) finish()
                    if (type== TypeItem){
                        ItemDetail(item = intent.getParcelableExtra(ExtraDataItemOrStage),viewModel)
                    }else{
                        StageDetail(stage = intent.getParcelableExtra(ExtraDataItemOrStage),viewModel)
                    }
                }
            }
        }
    }
}