package com.kevin.rhodesislandassist.util.json

import com.kevin.rhodesislandassist.models.Character
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

fun getCharactersFromJson(jsonObject: JSONObject): List<Character> {
    val charactersOut = mutableListOf<Character>()
    jsonObject.keys().forEach {
        val character = jsonObject.getJSONObject(it)
        try {
            charactersOut.add(
                Character(
                    character.getString("name"),
                    character.getString("description"),
                    character.getString("nationId"),
                    getStringListFromJson(character.getJSONArray("tagList")),
                    Character.Position.valueOf(character.getString("position")),
                    character.getString("itemUsage"),
                    Character.Profession.valueOf(character.getString("profession")),
                    character.getString("subProfessionId"),
                    character.getInt("rarity"),
                    getPhasesFromJson(character.getJSONArray("phases")),
                    getStringListFromJson(character.getJSONArray("skills")),
                    getTalentListFromJson(character.getJSONArray("talents"))
                )
            )
        } catch (e: JSONException) {
            return@forEach
        }
    }
    return charactersOut
}

private fun getStringListFromJson(jsonArray: JSONArray): List<String> {
    val list = mutableListOf<String>()
    for (i in 0 until jsonArray.length()) {
        list.add(jsonArray.getString(i))
    }
    return list
}

private fun getPhasesFromJson(jsonArray: JSONArray): List<Character.Phase> {
    val list = mutableListOf<Character.Phase>()
    for (i in 0 until jsonArray.length()) {
        val phaseJson = jsonArray.getJSONObject(i)
        val keyFramesJson = phaseJson.getJSONArray("attributesKeyFrames")
        list.add(
            Character.Phase(
                phaseJson.getInt("maxLevel"),
                getAttributeFromJson(keyFramesJson.getJSONObject(0)),
                getAttributeFromJson(keyFramesJson.getJSONObject(1))
            )
        )
    }
    return list
}

private fun getAttributeFromJson(jsonObject: JSONObject): Character.Attribute {
    val data = jsonObject.getJSONObject("data")
    return Character.Attribute(
        data.getInt("maxHp"),
        data.getInt("atk"),
        data.getInt("def"),
        data.getInt("magicResistance"),
        data.getInt("cost"),
        data.getInt("attackSpeed"),
        data.getInt("respawnTime")
    )
}

private fun getTalentListFromJson(jsonArray: JSONArray): List<Character.Talent> {
    val list = mutableListOf<Character.Talent>()
    for (i in 0 until jsonArray.length()) {
        list.add(
            Character.Talent(
                getCandidatesFromJson(
                    jsonArray.getJSONObject(i).getJSONArray("candidates")
                )
            )
        )
    }
    return list
}

private fun getCandidatesFromJson(jsonArray: JSONArray): List<Character.Talent.Candidate> {
    val list = mutableListOf<Character.Talent.Candidate>()
    for (i in 0 until jsonArray.length()) {
        val candidate = jsonArray.getJSONObject(i)
        val unlockCondition = candidate.getJSONObject("unlockCondition")
        list.add(
            Character.Talent.Candidate(
                unlockCondition.getInt("phase"),
                unlockCondition.getInt("level"),
                candidate.getString("name"),
                candidate.getString("description")
            )
        )
    }
    return list
}