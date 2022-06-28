package com.kevin.rhodesislandassist.util.json

import com.kevin.rhodesislandassist.models.Difficulty
import com.kevin.rhodesislandassist.models.GameStage
import com.kevin.rhodesislandassist.models.StageType
import org.json.JSONObject

/**
 * This will be used in future versions when hot load of json data will be implemented
 */

fun getStagesFromJson(jsonObject: JSONObject): List<GameStage> {
    val stages = jsonObject.getJSONObject("stages")
    val stagesOut = mutableListOf<GameStage>()
    stages.keys().forEach { key ->
        val stage = stages.getJSONObject(key)
        stagesOut.add(
            GameStage(
                stageId = stages.getString("stageId"),
                code = stages.getString("code"),
                name = stages.getString("name"),
                desc = stages.getString("description"),
                difficulty = Difficulty.valueOf(stages.getString("difficulty")),
                type = StageType.valueOf(stages.getString("stageType")),
                apCost = stages.getInt("apCost"),
                stageDrops = getStageDropsFromJson(stages.getJSONObject("stageDropInfo"))
            )
        )
    }
    return stagesOut
}

private fun getStageDropsFromJson(jsonObject: JSONObject): List<GameStage.DropInfo> {
    val dropInfo = jsonObject.getJSONArray("displayRewards")
    val dropInfoOut = mutableListOf<GameStage.DropInfo>()
    for (i in 0..dropInfo.length()) {
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