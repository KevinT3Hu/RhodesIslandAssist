package com.kevin.rhodesislandassist.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.kevin.rhodesislandassist.R
import com.kevin.rhodesislandassist.ui.theme.RhodesIslandAssistTheme
import com.kevin.rhodesislandassist.util.launchUrl

class AboutActivity : ComponentActivity() {

    private val psUrl="https://penguin-stats.cn"
    private val arkPlannerUrl="https://github.com/penguin-statistics/ArkPlanner"
    private val projectGitHubUrl="https://github.com/KevinT3Hu/RhodesIslandAssist"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RhodesIslandAssistTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(painter = painterResource(id = R.drawable.ic_logo), contentDescription = null,modifier=Modifier.padding(vertical = 5.dp))
                        Text(text = "${stringResource(id = R.string.hint_current_version)}:${packageManager.getPackageInfo(packageName,0).versionName}", modifier = Modifier.padding(vertical = 5.dp))
                        Text(text = stringResource(id = R.string.text_based_on_ps), modifier = Modifier.padding(vertical = 5.dp))

                    }
                    Column(modifier = Modifier.paddingFromBaseline(bottom = 45.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier
                            .padding(horizontal = 50.dp)
                            .fillMaxWidth()){
                            Text(text = stringResource(id = R.string.text_github_link))
                            Image(
                                painter = painterResource(id = R.drawable.ic_github_logo),
                                contentDescription = null,
                                modifier = Modifier
                                    .clickable {
                                        launchUrl(this@AboutActivity,projectGitHubUrl)
                                    }
                                    .size(35.dp)
                            )
                        }
                        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.padding(horizontal = 50.dp).padding(top = 10.dp).fillMaxWidth()) {
                            Text(text = stringResource(id = R.string.hint_ps), textDecoration = TextDecoration.Underline, modifier = Modifier.clickable {
                                launchUrl(this@AboutActivity,psUrl)
                            })
                            Text(text = stringResource(id = R.string.hint_arkplanner), textDecoration = TextDecoration.Underline, modifier = Modifier.clickable {
                                launchUrl(this@AboutActivity,arkPlannerUrl)
                            })
                        }
                    }
                }
            }
        }
    }
}