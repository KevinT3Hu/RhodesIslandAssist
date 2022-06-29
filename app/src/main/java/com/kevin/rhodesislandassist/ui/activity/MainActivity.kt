package com.kevin.rhodesislandassist.ui.activity

import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.kevin.rhodesislandassist.ui.component.App
import com.kevin.rhodesislandassist.ui.theme.RhodesIslandAssistTheme
import com.kevin.rhodesislandassist.ui.viewmodel.DataViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(window) {
            requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)

            exitTransition = Slide(Gravity.START)
            allowEnterTransitionOverlap = true
        }
        setContent {
            RhodesIslandAssistTheme {
                val viewModel: DataViewModel by viewModels()
                viewModel.initDataSet(this)
                App(viewModel)
            }
        }
    }
}
