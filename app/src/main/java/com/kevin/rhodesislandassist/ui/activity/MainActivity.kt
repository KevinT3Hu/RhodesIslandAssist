package com.kevin.rhodesislandassist.ui.activity

import android.os.Bundle
import android.transition.Explode
import android.transition.Slide
import android.transition.Transition
import android.view.Gravity
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kevin.rhodesislandassist.DataSetRepository
import com.kevin.rhodesislandassist.ui.component.App
import com.kevin.rhodesislandassist.ui.theme.RhodesIslandAssistTheme
import com.kevin.rhodesislandassist.ui.viewmodel.DataViewModel
import com.kevin.rhodesislandassist.util.AssetLoader

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(window){
            requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)

            exitTransition=Slide(Gravity.START)
            allowEnterTransitionOverlap=true
        }
        setContent {
            RhodesIslandAssistTheme {
                val viewModel:DataViewModel by viewModels()
                App(viewModel)
            }
        }
    }
}
