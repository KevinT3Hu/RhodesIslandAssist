package com.kevin.rhodesislandassist.util.json

import com.kevin.rhodesislandassist.models.Difficulty
import com.kevin.rhodesislandassist.models.GameStage
import com.kevin.rhodesislandassist.models.StageType
import org.json.JSONObject


fun getStagesFromJson(jsonObject: JSONObject): List<GameStage> {
    val stages = jsonObject.getJSONObject("stages")
    val stagesOut = mutableListOf<GameStage>()
    stages.keys().forEach { key ->
        val stage = stages.getJSONObject(key)
        val stageType: StageType
        try {
            stageType = StageType.valueOf(stage.getString("stageType"))
        } catch (e: IllegalArgumentException) {
            return@forEach
        }
        stagesOut.add(
            GameStage(
                stageId = stage.getString("stageId"),
                code = stage.getString("code"),
                name = stage.getString("name"),
                desc = stage.getString("description"),
                difficulty = Difficulty.valueOf(stage.getString("difficulty")),
                type = stageType,
                apCost = stage.getInt("apCost"),
                stageDrops = getStageDropsFromJson(stage.getJSONObject("stageDropInfo"))
            )
        )
    }
    return stagesOut
}

private fun getStageDropsFromJson(jsonObject: JSONObject): List<GameStage.DropInfo> {
    val dropInfo = jsonObject.getJSONArray("displayRewards")
    val dropInfoOut = mutableListOf<GameStage.DropInfo>()
    for (i in 0 until dropInfo.length()) {
        val dropInfoItem = dropInfo[i] as JSONObject
        var dropType: GameStage.DropType? = null
        when (dropInfoItem.getInt("dropType")) {
            1 -> dropType = GameStage.DropType.FIRST_TIME
            2 -> dropType = GameStage.DropType.COMMON
            3 -> dropType = GameStage.DropType.MINOR_CHANCE
            4 -> dropType = GameStage.DropType.SMALL_CHANCE
            else -> {}
        }
        if (dropType != null) {
            dropInfoOut.add(
                GameStage.DropInfo(
                    id = dropInfoItem.getString("id"),
                    dropType = dropType
                )
            )
        }
    }
    return dropInfoOut
}