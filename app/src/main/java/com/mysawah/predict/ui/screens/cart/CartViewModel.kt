package com.mysawah.predict.ui.screens.cart

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mysawah.predict.data.remote.ApiConfig
import com.mysawah.predict.data.repository.CartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    fun payOrder(context: Context, onSuccess: () -> Unit) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response = ApiConfig.getLaravelApi(context).payOrder()

                if (response.isSuccessful) {
                    Toast.makeText(context, "Pembayaran Berhasil!", Toast.LENGTH_SHORT).show()

                    CartRepository.clearCart()
                    onSuccess()
                } else {
                    Toast.makeText(context, "Gagal Bayar / Tidak ada tagihan", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
            _loading.value = false
        }
    }
    fun cancelOrder(context: Context) {
        _loading.value = true
        viewModelScope.launch {
            try {
                CartRepository.clearCartServer(context)
                Toast.makeText(context, "Keranjang dibersihkan", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(context, "Gagal: ${e.message}", Toast.LENGTH_SHORT).show()
            }
            _loading.value = false
        }
    }
}