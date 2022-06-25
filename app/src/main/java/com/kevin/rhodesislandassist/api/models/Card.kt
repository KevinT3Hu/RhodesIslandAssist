package com.kevin.rhodesislandassist.api.models

data class Card(
    val pool: String,
    val name: String,
    val rarity: Int,
    val isNew: Boolean
)