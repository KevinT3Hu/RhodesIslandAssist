package com.kevin.rhodesislandassist.api

import com.kevin.rhodesislandassist.api.models.MatrixResponse
import com.kevin.rhodesislandassist.api.models.StageDrop
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PenguinLogisticsApi {

    companion object{

        const val BaseUrl="https://penguin-stats.io/PenguinStats/api/"

        private const val MatrixApiRequestUrl="v2/result/matrix/"
        private const val ReportApiRequestUrl="v2/report/"
    }

    @GET(MatrixApiRequestUrl)
    fun getItemInfo(@Query("itemFilter")itemId:String):Call<MatrixResponse>

    @GET(MatrixApiRequestUrl)
    fun getStageInfo(@Query("stageFilter")stageId:String):Call<MatrixResponse>

    @POST(ReportApiRequestUrl)
    fun postStageDrop(@Body drop: StageDrop): Call<Any>
}