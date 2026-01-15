package com.mysawah.predict.data.repository

import com.mysawah.predict.data.remote.ApiConfig
import com.mysawah.predict.domain.model.ProductRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

object ProductRepository {

    suspend fun getProducts(): Response<List<ProductRequest>> {
        return withContext(Dispatchers.IO) {
            val response = ApiConfig.laravelapi.getProducts()

            if (response.isSuccessful && response.body() != null) {
                Response.success(response.body()!!.data)
            } else {
                Response.error(response.code(), response.errorBody()!!)
            }
        }
    }

    suspend fun getProductById(id: Int): Response<ProductRequest> {
        return withContext(Dispatchers.IO) {
            val response = ApiConfig.laravelapi.getProductById(id)

            if (response.isSuccessful && response.body() != null) {
                Response.success(response.body()!!.data)
            } else {
                Response.error(response.code(), response.errorBody()!!)
            }
        }
    }
}
