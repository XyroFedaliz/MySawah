package com.mysawah.predict.data.repository

import android.content.Context
import com.mysawah.predict.data.remote.ApiConfig
import com.mysawah.predict.domain.model.CheckoutItem
import com.mysawah.predict.domain.model.CheckoutRequest
import com.mysawah.predict.domain.model.ProductRequest
import com.mysawah.predict.domain.model.CartItemDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object CartRepository {

    // StateFlow untuk menampung data keranjang agar UI (CartScreen & FloatingButton) otomatis update
    private val _cartItems = MutableStateFlow<List<CartItemDetail>>(emptyList())
    val cartItems = _cartItems.asStateFlow()

     // 1. FETCH CART
     // Dipanggil saat HomeScreen atau CartScreen dibuka.
     // Mengambil data 'PENDING' dari database Laravel.
    suspend fun fetchCartFromServer(context: Context) {
        try {
            val response = ApiConfig.getLaravelApi(context).getCart()
            if (response.isSuccessful && response.body()?.data != null) {
                val serverItems = response.body()!!.data!!

                // Mapping dari Data Server (OrderDetail) ke Data UI (CartItemDetail)
                val mappedItems = serverItems.map { detail ->
                    CartItemDetail(
                        product = detail.produk,
                        qty = detail.jumlah
                    )
                }
                _cartItems.value = mappedItems
            } else {
                _cartItems.value = emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

     // 2. TAMBAH ITEM / INCREASE QTY
     // Dipanggil saat tombol (+) ditekan.
     // Kirim request ke server, jika sukses -> Ambil data terbaru (Sync).
    suspend fun increaseItem(context: Context, product: ProductRequest) {
        try {
            // Request checkout
            val item = CheckoutItem(id_produk = product.id_produk, jumlah = 1)
            val request = CheckoutRequest(items = listOf(item))

            // Panggil API checkout
            val response = ApiConfig.getLaravelApi(context).addToCart(request)

            if (response.isSuccessful) {
                fetchCartFromServer(context)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

     // 3. KURANGI ITEM / DECREASE QTY
     // Dipanggil saat tombol (-) ditekan.
     // Kirim request reduce ke server, jika sukses -> Ambil data terbaru (Sync).
    suspend fun decreaseItem(context: Context, product: ProductRequest) {
        try {
            val response = ApiConfig.getLaravelApi(context).reduceItem(product.id_produk)

            if (response.isSuccessful) {
                // Sync data terbaru
                fetchCartFromServer(context)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun clearCartServer(context: Context) {
        try {
            val response = ApiConfig.getLaravelApi(context).clearCart()
            if (response.isSuccessful) {
                clearCart()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }

}