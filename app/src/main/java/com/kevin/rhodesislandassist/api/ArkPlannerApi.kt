package com.kevin.rhodesislandassist.api

import com.kevin.rhodesislandassist.api.models.Plan
import com.kevin.rhodesislandassist.api.models.PlannerSubmission
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ArkPlannerApi {
    companion object{
        const val BaseUrl="https://planner.penguin-stats.io/"
    }

    @POST("plan/")
    fun getPlan(@Body submission: PlannerSubmission):Call<Plan>
}