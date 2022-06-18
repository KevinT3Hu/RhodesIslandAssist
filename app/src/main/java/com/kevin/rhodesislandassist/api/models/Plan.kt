package com.kevin.rhodesislandassist.api.models

import androidx.annotation.Keep

@Keep
data class Plan(
    val cost:Int,
    val gcost:Int,
    val gold:Int,
    val exp:Int,
    val stages:List<Stage>,
    val syntheses:List<Synthesis>
){
    data class Stage(
        val stage:String,
        val count:Float,
        val items:Map<String,Float>
    )

    data class Synthesis(
        val target:String,
        val count:Float,
        val materials:Map<String,Float>
    )
}
