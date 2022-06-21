package com.kevin.rhodesislandassist.ui.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.kevin.rhodesislandassist.DataSetRepository
import com.kevin.rhodesislandassist.R
import com.kevin.rhodesislandassist.api.PenguinLogisticsApi
import com.kevin.rhodesislandassist.api.models.StageDrop
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UploadViewModel : ViewModel() {
    private val stageDrop = mutableStateOf(StageDrop("", StageDrop.CN, mutableListOf()))

    private val api = Retrofit.Builder()
        .baseUrl(PenguinLogisticsApi.BaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PenguinLogisticsApi::class.java)

    fun removeDrop(dropType: String, id: String) {
        stageDrop.value.drops.removeIf {
            it.itemId == id && it.dropType == dropType
        }
    }

    fun updateDrop(dropType: String, id: String, count: Int) {
        removeDrop(dropType, id)
        stageDrop.value.drops.add(StageDrop.Drop(dropType, id, count))
    }

    fun getItemById(itemId: String) = DataSetRepository.gameItemDataSet!![itemId]

    fun report(context: Context, id: String) {
        stageDrop.value.stageId = id
        api.postStageDrop(stageDrop.value).enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if (response.code() == 200 || response.code() == 201) {
                    //success
                    Toast.makeText(context, R.string.toast_report_success, Toast.LENGTH_SHORT)
                        .show()
                } else {
                    response.errorBody()?.let { Log.i("report", it.string()) }
                    Toast.makeText(context, R.string.toast_report_fail, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(context, R.string.toast_network_fail, Toast.LENGTH_SHORT).show()
            }
        })
    }
}