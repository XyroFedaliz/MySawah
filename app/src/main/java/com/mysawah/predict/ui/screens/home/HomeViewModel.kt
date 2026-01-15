package com.mysawah.predict.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mysawah.predict.data.repository.ProductRepository
import com.mysawah.predict.domain.model.ProductRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _products = MutableStateFlow<List<ProductRequest>>(emptyList())
    val products = _products.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun getProducts() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = ProductRepository.getProducts()
                if (response.isSuccessful) {
                    _products.value = response.body() ?: emptyList()
                } else {
                    _error.value = "Gagal memuat produk!"
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Terjadi kesalahan!"
            }
            _loading.value = false
        }
    }

    fun clearError() {
        _error.value = null
    }
}
