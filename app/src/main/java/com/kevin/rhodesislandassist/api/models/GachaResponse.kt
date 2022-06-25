package com.kevin.rhodesislandassist.api.models

data class GachaResponse(
    val cards: List<Card>,
    val currentPage: Int,
    val total: Int
)