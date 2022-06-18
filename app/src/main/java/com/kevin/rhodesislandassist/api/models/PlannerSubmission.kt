package com.kevin.rhodesislandassist.api.models

import androidx.annotation.Keep

@Keep
data class PlannerSubmission(
    val owned:Map<String,Int>,
    val required:Map<String,Int>,
    val store:Boolean=false,
    val exclude:List<String> = listOf(),
    val exp_demand:Boolean=true,
    val gold_demand:Boolean=true,
    val extra_outc:Boolean=false,
)
