package com.kevin.rhodesislandassist.util

import android.content.Context
import androidx.annotation.Keep
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.kevin.rhodesislandassist.models.Difficulty
import com.kevin.rhodesislandassist.models.GameItem
import com.kevin.rhodesislandassist.models.GameStage
import java.lang.reflect.Type

class AssetLoader {
    companion object{

        private const val ItemTableName="item_table.json"
        private const val StageTableName="stage_table.json"

        fun loadGameItems(context: Context): Map<String,GameItem> {
            val inputStream = context.assets.open(ItemTableName)
            val gson = Gson()
            val itemListType=object: TypeToken<List<GameItem>>(){}.type
            val list:List<GameItem> = gson.fromJson(inputStream.reader(), itemListType)
            val map:MutableMap<String,GameItem> = mutableMapOf()
            list.forEach {
                if ((it.itemType==GameItem.ItemType.MATERIAL || it.itemType==GameItem.ItemType.CARD_EXP)&&it.itemId!=null){
                    map[it.itemId] = it
                }
            }
            return map.toMap()
        }

        fun loadGameStages(context: Context):Map<String,GameStage>{
            val inputStream=context.assets.open(StageTableName)
            val gson=GsonBuilder().registerTypeAdapter(GameStage::class.java,GameStageDeserializer())
            val stageListType=object :TypeToken<List<GameStage>>(){}.type
            val list:List<GameStage> = gson.create().fromJson(inputStream.reader(),stageListType)
            val map:MutableMap<String,GameStage> = mutableMapOf()
            list.forEach {
                if (it.difficulty==Difficulty.NORMAL&&it.stageId!=null){
                    map[it.stageId]=it
                }
            }
            return map.toMap()
        }
    }
}