package com.kevin.rhodesislandassist.api.models

import androidx.annotation.Keep
import com.kevin.rhodesislandassist.BuildConfig

@Keep
data class StageDrop(
    var stageId: String,
    val server: String,
    val drops: MutableList<Drop>,
    val version: String = "${BuildConfig.VERSION_CODE}",
    val source: String = "Android"
){
    data class Drop(
        val dropType:String,
        val itemId:String,
        var quantity:Int
    )
    companion object{
        val CN="CN"
        val US="US"
        val JP="JP"
        val KR="KR"
    }
}
