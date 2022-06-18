package com.kevin.rhodesislandassist.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.kevin.rhodesislandassist.DataSetRepository
import com.kevin.rhodesislandassist.api.ArkPlannerApi
import com.kevin.rhodesislandassist.api.models.Plan
import com.kevin.rhodesislandassist.api.models.PlannerSubmission
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PlannerViewModel:ViewModel() {

    private val api = Retrofit.Builder()
        .baseUrl(ArkPlannerApi.BaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ArkPlannerApi::class.java)

    val planStatus= mutableStateOf(false)
    val plan= mutableStateOf<Plan?>(null)

    val selectedRequiredMaterials = mutableStateMapOf<String,Int>()
    val selectedOwnedMaterials = mutableStateMapOf<String,Int>()

    fun addRequiredItem(itemName:String,count:Int=1){
        selectedRequiredMaterials[itemName]=count
    }

    fun addOwnedItem(itemName:String,count: Int=1){
        selectedOwnedMaterials[itemName]=count
    }

    fun getShowUnselectedRequiredMaterials():List<String>{
        val items= mutableListOf<String>()
        DataSetRepository.gameItemDataSet?.forEach {
            if (!selectedRequiredMaterials.containsKey(it.value.name)){
                items.add(it.value.name!!)
            }
        }
        return items
    }

    fun getShowUnselectedOwnedMaterials():List<String>{
        val items= mutableListOf<String>()
        DataSetRepository.gameItemDataSet?.forEach{
            if (!selectedOwnedMaterials.containsKey(it.value.name)){
                items.add(it.value.name!!)
            }
        }
        return items
    }

    fun changeRequiredMaterialCount(item:String,newCount:Int){
        if (selectedRequiredMaterials.containsKey(item)){
            if (newCount==0){
                selectedRequiredMaterials.remove(item)
            } else selectedRequiredMaterials[item]=newCount
        }
    }

    fun changeOwnedMaterialCount(item:String,newCount:Int){
        if (selectedOwnedMaterials.containsKey(item)){
            if (newCount==0){
                selectedOwnedMaterials.remove(item)
            } else selectedOwnedMaterials[item]=newCount
        }
    }

    fun getPlan(){
        planStatus.value=false
        val submission=PlannerSubmission(
            owned = selectedOwnedMaterials,
            required = selectedRequiredMaterials,
        )
        api.getPlan(submission).enqueue(object :Callback<Plan>{
            override fun onResponse(call: Call<Plan>, response: Response<Plan>){
                if (response.isSuccessful){
                    planStatus.value=true
                    plan.value=response.body()
                }
            }

            override fun onFailure(call: Call<Plan>, t: Throwable) {
                t.printStackTrace()
                call.request().url().pathSegments().forEach {
                    Log.e("NETWORK",it)
                }
            }
        })
    }
}