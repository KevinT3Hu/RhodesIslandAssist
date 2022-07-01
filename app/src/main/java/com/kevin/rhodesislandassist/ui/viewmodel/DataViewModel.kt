package com.kevin.rhodesislandassist.ui.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevin.rhodesislandassist.DataSetRepository.characterDataSet
import com.kevin.rhodesislandassist.DataSetRepository.gameItemDataSet
import com.kevin.rhodesislandassist.DataSetRepository.gameStageDataSet
import com.kevin.rhodesislandassist.R
import com.kevin.rhodesislandassist.models.Character
import com.kevin.rhodesislandassist.models.GameItem
import com.kevin.rhodesislandassist.models.GameStage
import com.kevin.rhodesislandassist.util.json.initData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DataViewModel : ViewModel() {

    var itemChipSelected by mutableStateOf(true)
    var stageChipSelected by mutableStateOf(true)
    var characterChipSelected by mutableStateOf(true)

    var searchText by mutableStateOf("")
    var gameItemSearchResultDataSet = mutableStateListOf<GameItem>()
    var gameStageSearchResultDataSet = mutableStateListOf<GameStage>()
    var characterSearchResultDataSet = mutableStateListOf<Character>()

    fun initDataSet(context: Context, forceRefresh: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = initData(context, forceRefresh)
            withContext(Dispatchers.Main) {
                if (!result) {
                    Toast.makeText(context, R.string.toast_network_fail, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun fetchDataFromSearchText(context: Context) {
        gameItemSearchResultDataSet.clear()
        gameStageSearchResultDataSet.clear()
        characterSearchResultDataSet.clear()
        if (itemChipSelected) {
            gameItemDataSet?.forEach { (_, gameItem) ->
                if (gameItem.name!!.contains(searchText)) {
                    gameItemSearchResultDataSet.add(gameItem)
                }
            }
        }
        if (stageChipSelected) {
            gameStageDataSet?.forEach { (_, gameStage) ->
                if (gameStage.name!!.contains(searchText) || gameStage.code!!.contains(
                        searchText
                    )
                ) {
                    gameStageSearchResultDataSet.add(gameStage)
                }
            }
        }
        if (characterChipSelected) {
            if (searchText.startsWith("tag:", ignoreCase = true)) {
                val tag = searchText.split(":")[1]
                characterDataSet?.forEach { (_, character) ->
                    if (character.tagList.contains(tag)) {
                        characterSearchResultDataSet.add(character)
                    }
                }
            } else if (searchText.startsWith("rarity:", ignoreCase = true)) {
                var rarity = 0
                try {
                    rarity = searchText.split(":")[1].toInt()
                } catch (e: NumberFormatException) {
                    return
                }
                characterDataSet?.forEach { (_, character) ->
                    if (character.rarity + 1 == rarity) {
                        characterSearchResultDataSet.add(character)
                    }
                }
            } else if (searchText.startsWith("prof", true) || searchText.startsWith(
                    "profession",
                    true
                )
            ) {
                val prof = searchText.split(":")[1]
                characterDataSet?.forEach { (_, character) ->
                    if (context.resources.getString(character.profession.getProfessionName()) == prof) {
                        characterSearchResultDataSet.add(character)
                    }
                }
            } else {
                characterDataSet?.forEach { (name, character) ->
                    if (name.contains(searchText)) {
                        characterSearchResultDataSet.add(character)
                    }
                }
            }
        }
    }

    fun getTotalNumberOfSearchedItems() =
        (if (itemChipSelected) gameItemSearchResultDataSet.size else 0) +
                (if (stageChipSelected) gameStageSearchResultDataSet.size else 0) +
                (if (characterChipSelected) characterSearchResultDataSet.size else 0)
}