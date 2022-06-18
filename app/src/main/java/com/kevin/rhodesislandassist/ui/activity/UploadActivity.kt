package com.kevin.rhodesislandassist.ui.activity

import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kevin.rhodesislandassist.ui.activity.ui.theme.RhodesIslandAssistTheme
import com.kevin.rhodesislandassist.ui.viewmodel.UploadViewModel
import com.kevin.rhodesislandassist.R
import com.kevin.rhodesislandassist.models.GameStage
import com.kevin.rhodesislandassist.ui.component.widget.NumberSelector
import java.util.function.Predicate

class UploadActivity : ComponentActivity() {

    companion object{
        const val ExtraStage="STAGE"
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(window){
            requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)

            enterTransition=Slide(Gravity.END)
        }
        val stage=intent.getParcelableExtra<GameStage>(ExtraStage)!!
        setContent {
            RhodesIslandAssistTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            SmallTopAppBar(
                                title = { Text(text = "${stringResource(id = R.string.title_activity_upload)}:${stage.code}-${stage.name}") }
                            )
                        }
                    ) { padding ->
                        val viewModel:UploadViewModel by viewModels()
                        Column(modifier = Modifier.padding(padding)) {
                            var commonDrops= listOf<GameStage.DropInfo>()
                            var extraDrops= listOf<GameStage.DropInfo>()
                            stage.stageDrops!!.partition {
                                it.dropType==GameStage.DropType.COMMON
                            }.apply {
                                commonDrops=first
                                extraDrops=second.partition {
                                    it.dropType!=GameStage.DropType.FIRST_TIME
                                }.first
                            }

                            Card {
                                Text(text = stringResource(id = R.string.hint_common_drop), modifier = Modifier
                                    .padding(5.dp)
                                    .padding(top = 5.dp))
                                LazyColumn{
                                    items(commonDrops){drop->
                                        val count = remember { mutableStateOf(0) }
                                        NumberSelector(
                                            value = count,
                                            onNumberChange = {newCount->
                                                if (newCount==0){
                                                    viewModel.removeDrop(drop.id!!)
                                                }else{

                                                }
                                            }
                                        )
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