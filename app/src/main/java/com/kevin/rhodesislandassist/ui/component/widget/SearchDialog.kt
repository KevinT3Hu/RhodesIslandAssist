package com.kevin.rhodesislandassist.ui.component.widget

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kevin.rhodesislandassist.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchDialog(show:MutableState<Boolean>, searchSource:List<String>, modifier: Modifier=Modifier, @StringRes title:Int= R.string.title_dialog_search, @StringRes confirmButtonText:Int=R.string.title_dialog_confirm, onConfirm:(List<String>)->Unit={}){

    val selected= mutableListOf<String>()
    val listSource= remember { mutableStateListOf<String>() }
    var searchText by remember { mutableStateOf("") }
    if (show.value){
        AlertDialog(
            onDismissRequest = { show.value=!show.value },
            title = { Text(text = stringResource(id = title)) },
            modifier = modifier.fillMaxHeight(0.7f),
            confirmButton = {
                TextButton(onClick = {
                    onConfirm(selected)
                    show.value=false
                }) {
                    Text(text = stringResource(id = confirmButtonText))
                }
            },
            text = {
                Column(modifier = Modifier.fillMaxSize()) {
                    TextField(value = searchText, onValueChange = {
                        searchText=it
                        listSource.clear()
                        if (it.isNotBlank()){
                            searchSource.forEach { source->
                                if (source.contains(it)){
                                    listSource.add(source)
                                }
                            }
                        }
                    })
                    LazyColumn(){
                        items(listSource){source->
                            Column(modifier= Modifier
                                    .fillMaxWidth()
                                    .clickable { }) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    var checkState by remember { mutableStateOf(false) }
                                    Checkbox(checked = checkState, onCheckedChange = {
                                        if (checkState) {
                                            checkState = false
                                            if (selected.contains(source)) {
                                                selected.remove(source)
                                            }
                                        } else {
                                            checkState = true
                                            selected.add(source)
                                        }
                                    }, modifier = Modifier.padding(10.dp))
                                    Text(text = source)
                                }
                            }
                        }
                    }
                }
            }
        )
    }
}