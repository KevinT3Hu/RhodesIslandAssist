package com.kevin.rhodesislandassist.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.kevin.rhodesislandassist.DataSetRepository
import com.kevin.rhodesislandassist.api.models.StageDrop

class UploadViewModel : ViewModel() {
    val stageDrop = mutableStateOf(StageDrop("", StageDrop.CN, mutableListOf()))

    fun removeDrop(id: String) {
        stageDrop.value.drops.removeIf {
            it.itemId == id
        }
    }

    fun updateDrop(id: String, count: Int) {

    }

    fun getItemById(itemId: String) = DataSetRepository.gameItemDataSet!![itemId]
}