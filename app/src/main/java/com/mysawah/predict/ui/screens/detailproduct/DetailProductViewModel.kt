package com.mysawah.predict.ui.screens.detailproduct

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mysawah.predict.data.repository.ProductRepository
import com.mysawah.predict.domain.model.ProductRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailProductViewModel : ViewModel() {

    private val _productRequest = MutableStateFlow<ProductRequest?>(null)
    val product = _productRequest.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val _otherProducts = MutableStateFlow<List<ProductRequest>>(emptyList())
    val otherProducts = _otherProducts.asStateFlow()

    private var currentProductId: Int? = null

    fun setProductId(id: Int) {
        if (currentProductId == id) return
        currentProductId = id
        loadProduct(id)
    }

    fun loadProduct(id: Int) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                val detailResponse = ProductRepository.getProductById(id)
                if (detailResponse.isSuccessful) {
                    _productRequest.value = detailResponse.body()
                }

                val productsResponse = ProductRepository.getProducts()
                if (productsResponse.isSuccessful) {
                    _otherProducts.value =
                        productsResponse.body()
                            ?.filter { it.id_produk != id }
                            ?: emptyList()
                }

            } catch (e: Exception) {
                _error.value = e.message
            }

            _loading.value = false
        }
    }

    fun clearError() {
        _error.value = null
    }
}

