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
import com.kevin.rhodesislandassist.ui.component.CharacterDetail
import com.kevin.rhodesislandassist.ui.component.ItemDetail
import com.kevin.rhodesislandassist.ui.component.StageDetail
import com.kevin.rhodesislandassist.ui.theme.RhodesIslandAssistTheme
import com.kevin.rhodesislandassist.ui.viewmodel.DetailViewModel

class DetailActivity : ComponentActivity() {

    companion object {
        const val ExtraTagType = "TYPE"
        const val TypeItem = 0
        const val TypeStage = 1
        const val TypeChar = 2
        const val TypeError = -1
        const val ExtraData = "DATA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(window) {
            requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)

            enterTransition = Slide(Gravity.END)
            exitTransition = Slide(Gravity.START)
        }
        setContent {
            RhodesIslandAssistTheme {
                val viewModel: DetailViewModel by viewModels()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val type = intent.getIntExtra(ExtraTagType, TypeError)
                    when (type) {
                        TypeItem -> {
                            ItemDetail(
                                item = intent.getParcelableExtra(ExtraData),
                                viewModel
                            )
                        }
                        TypeStage -> {
                            StageDetail(
                                stage = intent.getParcelableExtra(ExtraData),
                                viewModel
                            )
                        }
                        TypeChar -> {
                            CharacterDetail(
                                character = intent.getParcelableExtra(ExtraData),
                                viewModel
                            )
                        }
                        TypeError -> finish()
                    }
                }
            }
        }
    }
}