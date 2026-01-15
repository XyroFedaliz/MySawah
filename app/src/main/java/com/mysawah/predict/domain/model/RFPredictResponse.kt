package com.mysawah.predict.domain.model

data class RFPredictResponse(
    val status: String,
    val model: String,
    val prediction: String
)
