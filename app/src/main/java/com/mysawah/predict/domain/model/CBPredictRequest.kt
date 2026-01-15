package com.mysawah.predict.domain.model

import com.google.gson.annotations.SerializedName

data class CBPredictRequest(

    @SerializedName("Source Node")
    val sourceNode: String,

    @SerializedName("Destination Node")
    val destinationNode: String,

    @SerializedName("Source Type")
    val sourceType: String
)
