package com.kevin.rhodesislandassist.util.json

import com.kevin.rhodesislandassist.models.GameItem
import org.json.JSONObject

fun getItemsFromJson(jsonObject: JSONObject): List<GameItem> {
    val items = jsonObject.getJSONObject("items")
    val itemsOut = mutableListOf<GameItem>()
    items.keys().forEach {
        val item = items.getJSONObject(it)
        itemsOut.add(
            GameItem(
                itemId = item.getString("itemId"),
                name = item.getString("name"),
                description = item.getString("description"),
                rarity = item.getInt("rarity"),
                iconId = item.getString("iconId"),
                usage = item.getString("usage"),
                itemType = item.getString("itemType")
            )
        )
    }
    return itemsOut
}