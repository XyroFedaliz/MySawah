package com.mysawah.predict.data.remote

import com.mysawah.predict.domain.model.UserRequest
import com.mysawah.predict.domain.model.BaseResponse
import com.mysawah.predict.domain.model.ProductResponse
import com.mysawah.predict.domain.model.RFPredictRequest
import com.mysawah.predict.domain.model.RFPredictResponse
import com.mysawah.predict.domain.model.CBPredictRequest
import com.mysawah.predict.domain.model.CBPredictResponse
import com.mysawah.predict.domain.model.ProductRequest
import com.mysawah.predict.domain.model.CheckoutRequest
import com.mysawah.predict.domain.model.HistoryOrder
import com.mysawah.predict.domain.model.OrderDetailResponse
import retrofit2.Response
import okhttp3.MultipartBody
import retrofit2.http.*

interface ApiService {

    // =====================
    // AUTH
    // =====================

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<BaseResponse<UserRequest>>

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<BaseResponse<UserRequest>>

    // =====================
    // PRODUCT API
    // =====================

    @GET("products")
    suspend fun getProducts(): Response<ProductResponse>

    @GET("products/{id}")
    suspend fun getProductById(
        @Path("id") id: Int
    ): Response<BaseResponse<ProductRequest>>


    @POST("rf/predict")
    suspend fun predictRandomForest(
        @Body body: RFPredictRequest
    ): Response<RFPredictResponse>

    @POST("cb/predict")
    suspend fun predictCatBoost(
        @Body body: CBPredictRequest
    ): Response<CBPredictResponse>

    @GET("user/me")
    suspend fun getUserProfile(
    ): Response<BaseResponse<UserRequest>>

    @FormUrlEncoded
    @PUT("user/profile")
    suspend fun updateProfile(
        @Field("name") name: String? = null,
        @Field("no_hp") noHp: String? = null,
        @Field("alamat") alamat: String? = null
    ): Response<BaseResponse<UserRequest>>

    @Multipart
    @POST("user/photo")
    suspend fun uploadPhoto(
        @Part foto_profil: MultipartBody.Part
    ): Response<BaseResponse<UserRequest>>

    // HAPUS FOTO
    @DELETE("user/photo")
    suspend fun deletePhoto(): Response<BaseResponse<Any>>


    @POST("checkout")
    suspend fun addToCart( // CheckoutRequest
        @Body request: CheckoutRequest
    ): Response<BaseResponse<Any>>

    // Dipanggil saat klik tombol "Bayar" di Keranjang
    @POST("pay")
    suspend fun payOrder(): Response<BaseResponse<Any>>

    @GET("cart")
    suspend fun getCart(): Response<BaseResponse<List<OrderDetailResponse>>>

    // Kurangi Item
    @FormUrlEncoded
    @POST("cart/reduce")
    suspend fun reduceItem(
        @Field("id_produk") idProduk: Int
    ): Response<BaseResponse<Any>>

    @GET("history")
    suspend fun getHistory(): Response<BaseResponse<List<HistoryOrder>>>

    @DELETE("cart/clear")
    suspend fun clearCart(): Response<BaseResponse<Any>>
}
