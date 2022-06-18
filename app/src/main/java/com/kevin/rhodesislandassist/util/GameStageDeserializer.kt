package com.kevin.rhodesislandassist.util

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.kevin.rhodesislandassist.models.Difficulty
import com.kevin.rhodesislandassist.models.GameStage
import com.kevin.rhodesislandassist.models.StageType
import java.lang.reflect.Type

class GameStageDeserializer : JsonDeserializer<GameStage> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): GameStage {
        if (json == null) {
            throw JsonParseException("Null Json")
        }
        val stageType = StageType.valueOf(json.asJsonObject["stageType"].asString)
        val stageDifficulty = when (json.asJsonObject["difficulty"].asString) {
            "NORMAL" -> Difficulty.NORMAL
            "FOUR_STAR" -> Difficulty.FOUR_STAR
            else -> Difficulty.NORMAL
        }
        val code = json.asJsonObject["code"].asString
        val stageId = json.asJsonObject["stageId"].asString
        val name = json.asJsonObject["name"].toString()
        val desc = json.asJsonObject["description"].toString()
        val apCost = json.asJsonObject["apCost"].asInt
        val stageDrops: MutableList<GameStage.DropInfo> = mutableListOf()
        json.asJsonObject["stageDropInfo"].asJsonObject["displayDetailRewards"].asJsonArray.forEach { dropInfoJson ->
            val id = dropInfoJson.asJsonObject["id"].asString
            val dropType = when (dropInfoJson.asJsonObject["dropType"].asInt) {
                1 -> GameStage.DropType.FIRST_TIME
                2 -> GameStage.DropType.COMMON
                3 -> GameStage.DropType.MINOR_CHANCE
                4 -> GameStage.DropType.SMALL_CHANCE
                else -> GameStage.DropType.COMMON
            }
            stageDrops.add(GameStage.DropInfo(id, dropType))
        }
        return GameStage(stageId, code, name, desc, stageDifficulty, stageType, apCost, stageDrops)
    }
}