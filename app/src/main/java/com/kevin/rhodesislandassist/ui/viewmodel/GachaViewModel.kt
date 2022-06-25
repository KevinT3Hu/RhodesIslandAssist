package com.kevin.rhodesislandassist.ui.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import com.kevin.rhodesislandassist.R
import com.kevin.rhodesislandassist.api.GachaApi
import com.kevin.rhodesislandassist.api.models.Card
import com.kevin.rhodesislandassist.api.models.GachaResponse
import com.kevin.rhodesislandassist.ui.component.widget.PieSlice
import com.kevin.rhodesislandassist.util.GachaResponseDeserializer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GachaViewModel : ViewModel() {

    val CardsPerPage = 10

    val gson =
        GsonBuilder().registerTypeAdapter(GachaResponse::class.java, GachaResponseDeserializer())
            .create()

    val api = Retrofit.Builder()
        .baseUrl(GachaApi.BaseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(GachaApi::class.java)

    val cards = mutableStateListOf<Card>()
    val pieSlices = mutableStateListOf<PieSlice>()
    val pools = mutableStateListOf<String>()

    val isFiltered = mutableStateOf(false)
    val cardsFiltered = mutableStateListOf<Card>()
    val pieSlicesFiltered = mutableStateListOf<PieSlice>()

    val loginStatus = mutableStateOf(false)
    val token = mutableStateOf("")

    fun fetchCards(context: Context) {
        api.getGacha(1, token.value).enqueue(object : Callback<GachaResponse> {
            override fun onResponse(call: Call<GachaResponse>, response: Response<GachaResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    cards.addAll(response.body()!!.cards)
                    for (i in 2..getPageCount(response.body()!!.total)) {
                        getPage(context, i, i == getPageCount(response.body()!!.total))
                    }
                    Log.i("gacha", "${response.body()!!.total}")
                }
            }

            override fun onFailure(call: Call<GachaResponse>, t: Throwable) {
                Toast.makeText(context, R.string.toast_network_fail, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun filterByPool(pool: String) {
        if (pools.contains(pool)) {
            isFiltered.value = true
            cardsFiltered.clear()
            pieSlicesFiltered.clear()
            val map = mutableMapOf(Pair(2, 0), Pair(3, 0), Pair(4, 0), Pair(5, 0))
            cards.forEach {
                if (it.pool == pool) {
                    cardsFiltered.add(it)
                    map[it.rarity] = map[it.rarity]!!.plus(1)
                }
            }
            map.forEach {
                pieSlicesFiltered.add(
                    PieSlice(
                        "${it.key}",
                        it.value.toFloat(),
                        getRarityColor(it.key)
                    )
                )
            }
        }
    }

    fun clearFilter() {
        isFiltered.value = false
        cardsFiltered.clear()
        pieSlicesFiltered.clear()
        cardsFiltered.addAll(cards)
        pieSlicesFiltered.addAll(pieSlices)
    }

    fun getRarityRate(rarity: Int): Float {
        var total = 0
        var count = 0
        pieSlicesFiltered.forEach {
            total += it.number.toInt()
            if (it.name == "$rarity") {
                count = it.number.toInt()
            }
        }
        return count * 100 / total.toFloat()
    }

    private fun getPage(context: Context, i: Int, isLast: Boolean) {
        api.getGacha(i, token.value).enqueue(object : Callback<GachaResponse> {
            override fun onResponse(call: Call<GachaResponse>, response: Response<GachaResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    cards.addAll(response.body()!!.cards)
                    if (isLast) {
                        getPieSlices()
                        cards.forEach {
                            if (!pools.contains(it.pool)) {
                                pools.add(it.pool)
                            }
                        }
                        cardsFiltered.addAll(cards)
                    }
                }
            }

            override fun onFailure(call: Call<GachaResponse>, t: Throwable) {
                Toast.makeText(context, R.string.toast_network_fail, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getPageCount(cardCount: Int) =
        if (cardCount % CardsPerPage == 0) cardCount / CardsPerPage else cardCount / CardsPerPage + 1

    private fun getPieSlices() {
        pieSlices.clear()
        val map = mutableMapOf(Pair(2, 0), Pair(3, 0), Pair(4, 0), Pair(5, 0))
        cards.forEach {
            map[it.rarity] = map[it.rarity]!!.plus(1)
        }
        map.forEach {
            pieSlices.add(PieSlice("${it.key}", it.value.toFloat(), getRarityColor(it.key)))
        }
        pieSlicesFiltered.addAll(pieSlices)
    }

    fun getRarityColor(rarity: Int) = when (rarity) {
        2 -> Color.Gray
        3 -> Color(147, 112, 219)
        4 -> Color.Red
        5 -> Color(255, 140, 0)
        else -> Color.White
    }
}