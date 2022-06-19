package com.kevin.rhodesislandassist.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.kevin.rhodesislandassist.ui.activity.ui.theme.RhodesIslandAssistTheme
import com.kevin.rhodesislandassist.R
import com.mikepenz.aboutlibraries.ui.compose.LibrariesContainer

class OpenSourceNoticesActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                                navigationIcon = { IconButton(onClick = { finish() }) {
                                    Icon(Icons.Filled.ArrowBack, contentDescription = null)
                                } },
                                title = { Text(text = stringResource(id = R.string.title_activity_open_source_notices)) }
                            )
                        }
                    ) {
                        LibrariesContainer(modifier = Modifier
                            .padding(it)
                            .fillMaxSize())
                    }
                }
            }
        }
    }
}