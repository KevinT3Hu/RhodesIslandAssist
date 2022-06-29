package com.kevin.rhodesislandassist.util.json

import android.content.Context
import com.kevin.rhodesislandassist.DataSetRepository
import com.kevin.rhodesislandassist.R
import com.kevin.rhodesislandassist.models.Difficulty
import com.kevin.rhodesislandassist.models.GameItem
import com.kevin.rhodesislandassist.models.GameStage
import com.kevin.rhodesislandassist.util.getCurrentDate
import org.apache.commons.io.IOUtil
import org.json.JSONObject
import java.io.File
import java.net.URL

const val ItemFileName = "item_table.json"
const val StageFileName = "stage_table.json"

const val PreferencesKeyRefresh = "refresh"

private const val FileDownloadBaseUrl = "https://kevin-game-data.oss-cn-hangzhou.aliyuncs.com"

fun initData(context: Context, forceRefresh: Boolean = false) {
    val itemFile = fileHandler(context, ItemFileName, forceRefresh)
    val items = getItemsFromJson(JSONObject(itemFile.readText()))
    val itemsMap = mutableMapOf<String, GameItem>()
    items.forEach {
        if ((it.itemType == GameItem.ItemType.MATERIAL || it.itemType == GameItem.ItemType.CARD_EXP) && it.itemId != null) {
            itemsMap[it.itemId] = it
        }
    }
    DataSetRepository.gameItemDataSet = itemsMap
    val stageFile = fileHandler(context, StageFileName, forceRefresh)
    val stages = getStagesFromJson(JSONObject(stageFile.readText()))
    val stagesMap = mutableMapOf<String, GameStage>()
    stages.forEach {
        if (it.difficulty == Difficulty.NORMAL && it.stageId != null) {
            stagesMap[it.stageId] = it
        }
    }
    DataSetRepository.gameStageDataSet = stagesMap
}

private fun fileHandler(context: Context, fileName: String, forceRefresh: Boolean): File {
    if (forceRefresh || (!(File(context.filesDir, fileName).exists()))) {
        context.getSharedPreferences(
            context.resources.getString(R.string.settings_preferences),
            Context.MODE_PRIVATE
        ).edit().putString(
            PreferencesKeyRefresh, getCurrentDate()
        ).apply()
        URL("${FileDownloadBaseUrl}/${fileName}").openStream().use {
            val json = IOUtil.toString(it)
            val file = File(context.filesDir, fileName)
            file.createNewFile()
            file.writeBytes(json.encodeToByteArray())
        }
    }
    return File(context.filesDir, fileName)
}