package com.kevin.rhodesislandassist

import com.kevin.rhodesislandassist.models.Character
import com.kevin.rhodesislandassist.models.GameItem
import com.kevin.rhodesislandassist.models.GameStage

object DataSetRepository {
    var gameItemDataSet: Map<String, GameItem>? = null
    var gameStageDataSet: Map<String, GameStage>? = null
    var characterDataSet: Map<String, Character>? = null
}