package com.kevin.rhodesislandassist.api.models

import androidx.annotation.Keep
import com.kevin.rhodesislandassist.DataSetRepository
import java.util.*

@Keep
data class Matrix(
    val stageId:String,
    val itemId:String,
    val quantity:Int,
    val times:Int,
    val start:Long,
    val end:Long
){
    fun dropDate()= quantity.toFloat() /times.toFloat()
    private fun expectedTimePerItem()=1/dropDate()
    fun expectedApCostPerItem(apCost:Int)=expectedTimePerItem()* apCost
    fun getStartDate()=Date(start)
    fun getEndDate()=Date(end)
}
