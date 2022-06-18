package com.kevin.rhodesislandassist

import com.kevin.rhodesislandassist.models.Difficulty
import com.kevin.rhodesislandassist.models.GameItem
import com.kevin.rhodesislandassist.models.GameStage

object DataSetRepository {
    var gameItemDataSet:Map<String,GameItem>? = null
    var gameStageDataSet:Map<String,GameStage>?=null
}