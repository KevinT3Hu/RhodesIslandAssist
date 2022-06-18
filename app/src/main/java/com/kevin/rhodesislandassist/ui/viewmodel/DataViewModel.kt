package com.kevin.rhodesislandassist.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.kevin.rhodesislandassist.DataSetRepository.gameItemDataSet
import com.kevin.rhodesislandassist.DataSetRepository.gameStageDataSet
import com.kevin.rhodesislandassist.models.GameItem
import com.kevin.rhodesislandassist.models.GameStage

class DataViewModel:ViewModel() {

    var itemChipSelected by mutableStateOf(true)
    var stageChipSelected by mutableStateOf(true)

    var searchText by mutableStateOf("")
    var gameItemSearchResultDataSet = mutableStateListOf<GameItem>()
    var gameStageSearchResultDataSet = mutableStateListOf<GameStage>()

    fun fetchDataFromSearchText(){
        gameItemSearchResultDataSet.clear()
        gameStageSearchResultDataSet.clear()
        if (itemChipSelected){
            gameItemDataSet?.forEach { gameItem ->
                if (gameItem.value.name!!.contains(searchText)){
                    gameItemSearchResultDataSet.add(gameItem.value)
                }
            }
        }
        if (stageChipSelected){
            gameStageDataSet?.forEach{gameStage ->
                if (gameStage.value.name!!.contains(searchText)||gameStage.value.code!!.contains(searchText)){
                    gameStageSearchResultDataSet.add(gameStage.value)
                }
            }
        }
    }
}