package com.mysawah.predict.domain.model

data class RFPredictRequest(
    val N: Float,
    val P: Float,
    val K: Float,
    val temperature: Float,
    val humidity: Float,
    val ph: Float,
    val rainfall: Float
)
