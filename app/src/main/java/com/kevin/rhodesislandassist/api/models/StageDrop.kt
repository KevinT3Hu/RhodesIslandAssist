package com.kevin.rhodesislandassist.api.models

import androidx.annotation.Keep

@Keep
data class StageDrop(
    val stageId:String,
    val server:String,
    val drops:MutableList<Drop>
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
