package com.kevin.rhodesislandassist.api

import com.kevin.rhodesislandassist.api.models.GachaResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GachaApi {
    companion object {
        const val BaseUrl = "https://ak.hypergryph.com/"
    }

    @GET("user/api/inquiry/gacha")
    fun getGacha(@Query("page") page: Int, @Query("token") token: String): Call<GachaResponse>
}