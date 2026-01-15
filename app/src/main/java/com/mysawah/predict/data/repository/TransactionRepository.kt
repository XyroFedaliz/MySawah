package com.mysawah.predict.data.repository

import android.content.Context
import com.mysawah.predict.data.remote.ApiConfig
import com.mysawah.predict.domain.model.BaseResponse
import com.mysawah.predict.domain.model.HistoryOrder
import retrofit2.Response

object TransactionRepository {

    suspend fun getHistory(context: Context): Response<BaseResponse<List<HistoryOrder>>> {
        return ApiConfig.getLaravelApi(context).getHistory()
    }
}