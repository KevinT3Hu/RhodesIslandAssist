package com.kevin.rhodesislandassist.api.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class MatrixResponse(
    @SerializedName("matrix")
    val matrixes:List<Matrix>
)
