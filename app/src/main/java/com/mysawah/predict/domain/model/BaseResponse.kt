package com.mysawah.predict.domain.model

data class BaseResponse<T>(
    val status: String,
    val message: String,
    val data: T?,
    val token: String? = null
)