package com.kevin.rhodesislandassist.ui.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.kevin.rhodesislandassist.DataSetRepository
import com.kevin.rhodesislandassist.R
import com.kevin.rhodesislandassist.api.PenguinLogisticsApi
import com.kevin.rhodesislandassist.api.models.Matrix
import com.kevin.rhodesislandassist.api.models.MatrixResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailViewModel : ViewModel() {

    private val api: PenguinLogisticsApi = Retrofit.Builder()
        .baseUrl(PenguinLogisticsApi.BaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PenguinLogisticsApi::class.java)

    var matrixes = mutableStateListOf<Matrix>()

    private val cachedItemMatrix: MutableMap<String, MatrixResponse> = mutableMapOf()
    private val cachedStageMatrix: MutableMap<String, MatrixResponse> = mutableMapOf()

    fun getItemMatrix(itemId: String, context: Context, refreshState: SwipeRefreshState) {
        //find if this is in cache
        if (cachedItemMatrix.containsKey(itemId)) {
            matrixes.clear()
            cachedItemMatrix[itemId]!!.matrixes.forEach {
                matrixes.add(it)
            }
            sortMatrixByExpectedApCost()
            return
        }
        refreshItem(itemId, context, refreshState)
    }

    fun refreshItem(itemId: String, context: Context, refreshState: SwipeRefreshState) {
        matrixes.clear()
        Log.i("Network", api.getItemInfo(itemId).request().url().toString())
        api.getItemInfo(itemId).enqueue(object : Callback<MatrixResponse> {
            override fun onResponse(
                call: Call<MatrixResponse>,
                response: Response<MatrixResponse>
            ) {
                Log.i("Network", "networkResponse")
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        response.body()!!.matrixes.forEach { matrix ->
                            if (matrix.stageId.endsWith("_perm")) {
                                val alteredMatrix = Matrix(
                                    matrix.stageId.dropLast(5),
                                    matrix.itemId,
                                    matrix.quantity,
                                    matrix.times,
                                    matrix.start,
                                    matrix.end
                                )
                                matrixes.add(alteredMatrix)
                            } else if (matrix.stageId.endsWith("_rep")) {
                                val alteredMatrix = Matrix(
                                    matrix.stageId.dropLast(4),
                                    matrix.itemId,
                                    matrix.quantity,
                                    matrix.times,
                                    matrix.start,
                                    matrix.end
                                )
                                matrixes.add(alteredMatrix)
                            } else if (!matrix.stageId.startsWith("randomMaterial")) {
                                matrixes.add(matrix)
                            }
                        }
                        cachedItemMatrix[itemId] = response.body()!!
                        sortMatrixByExpectedApCost()
                    }
                }
                refreshState.isRefreshing = false
            }

            override fun onFailure(call: Call<MatrixResponse>, t: Throwable) {
                Log.i("Network", "NetworkFail")
                refreshState.isRefreshing = false
                Toast.makeText(context, R.string.toast_network_fail, Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun sortMatrixByExpectedApCost() {
        matrixes.sortBy {
            it.expectedApCostPerItem(DataSetRepository.gameStageDataSet!![it.stageId]!!.apCost)
        }
    }

    fun getStageMatrix(stageId: String, context: Context, refreshState: SwipeRefreshState) {
        if (cachedStageMatrix.containsKey(stageId)) {
            matrixes.clear()
            cachedStageMatrix[stageId]!!.matrixes.forEach {
                matrixes.add(it)
            }
            return
        }
        refreshStage(stageId, context, refreshState)
    }

    fun refreshStage(
        stageId: String,
        context: Context,
        refreshState: SwipeRefreshState,
        mayCallWithSuffix: Boolean = true
    ) {
        matrixes.clear()
        api.getStageInfo(stageId).enqueue(object : Callback<MatrixResponse> {
            override fun onResponse(
                call: Call<MatrixResponse>,
                response: Response<MatrixResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        if (response.body()!!.matrixes.isEmpty() and mayCallWithSuffix) {
                            refreshStageWithSuffix(stageId, context, refreshState)
                            return
                        }
                        response.body()!!.matrixes.forEach { matrix ->
                            if (matrix.itemId != "furni" && !matrix.itemId.startsWith("ap_supply") && !matrix.itemId.startsWith(
                                    "randomMaterial"
                                )
                            ) {
                                matrixes.add(matrix)
                            }
                        }
                        cachedStageMatrix[stageId] = response.body()!!
                    }
                }
                refreshState.isRefreshing = false
            }

            override fun onFailure(call: Call<MatrixResponse>, t: Throwable) {
                refreshState.isRefreshing = false
                Toast.makeText(context, R.string.toast_network_fail, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun refreshStageWithSuffix(
        stageId: String,
        context: Context,
        refreshState: SwipeRefreshState
    ) {
        refreshStage("${stageId}_perm", context, refreshState, false)
    }

    fun getStageById(stageId: String) = DataSetRepository.gameStageDataSet!![stageId]

    fun getItemById(itemId: String) = DataSetRepository.gameItemDataSet!![itemId]
}