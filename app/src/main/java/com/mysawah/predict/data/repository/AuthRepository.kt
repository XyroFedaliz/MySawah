package com.mysawah.predict.data.repository

import android.content.Context
import com.mysawah.predict.data.remote.ApiConfig
import com.mysawah.predict.domain.model.BaseResponse
import com.mysawah.predict.domain.model.UserRequest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import retrofit2.Response

object AuthRepository {

    suspend fun register(name: String, email: String, password: String):
            Response<BaseResponse<UserRequest>> {

        return ApiConfig.laravelapi.register(
            name = name,
            email = email,
            password = password
        )
    }

    suspend fun login(email: String, password: String):
            Response<BaseResponse<UserRequest>> {

        return ApiConfig.laravelapi.login(
            email = email,
            password = password
        )
    }

    suspend fun getUserProfile(context: Context): Response<BaseResponse<UserRequest>> {
        return ApiConfig.getLaravelApi(context).getUserProfile()
    }

    suspend fun updateUser(
        context: Context,
        name: String? = null,
        noHp: String? = null,
        alamat: String? = null
    ): Response<BaseResponse<UserRequest>> {
        return ApiConfig.getLaravelApi(context).updateProfile(name, noHp, alamat)
    }

    suspend fun uploadPhoto(context: Context, file: File): Response<BaseResponse<UserRequest>> {
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("foto_profil", file.name, requestFile)
        return ApiConfig.getLaravelApi(context).uploadPhoto(body)
    }

    suspend fun deletePhoto(context: Context): Response<BaseResponse<Any>> {
        return ApiConfig.getLaravelApi(context).deletePhoto()
    }

}
