package com.kevin.rhodesislandassist.util

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.kevin.rhodesislandassist.api.models.Card
import com.kevin.rhodesislandassist.api.models.GachaResponse
import java.lang.reflect.Type

class GachaResponseDeserializer : JsonDeserializer<GachaResponse> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): GachaResponse {
        if (json == null) throw JsonParseException("Null json")
        val data = json.asJsonObject.get("data").asJsonObject
        val cards = data.get("list").asJsonArray
        val cardsOut = mutableListOf<Card>()
        cards.forEach {
            val pool = it.asJsonObject.get("pool").asString
            val chars = it.asJsonObject.get("chars").asJsonArray[0]
            val name = chars.asJsonObject.get("name").asString
            val rarity = chars.asJsonObject.get("rarity").asInt
            val isNew = chars.asJsonObject.get("isNew").asBoolean
            cardsOut.add(Card(pool, name, rarity, isNew))
        }
        val currentPage = data.get("pagination").asJsonObject.get("current").asInt
        val totalPage = data.get("pagination").asJsonObject.get("total").asInt
        return GachaResponse(cardsOut, currentPage, totalPage)
    }

}