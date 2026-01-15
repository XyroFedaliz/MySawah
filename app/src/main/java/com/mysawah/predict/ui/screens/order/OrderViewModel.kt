package com.mysawah.predict.ui.screens.order

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mysawah.predict.data.repository.TransactionRepository
import com.mysawah.predict.domain.model.HistoryOrder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OrderViewModel : ViewModel() {

    private val _historyList = MutableStateFlow<List<HistoryOrder>>(emptyList())
    val historyList = _historyList.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun getHistory(context: Context) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = TransactionRepository.getHistory(context)
                if (response.isSuccessful && response.body()?.data != null) {
                    _historyList.value = response.body()!!.data!!
                } else {
                    _error.value = "Gagal memuat riwayat"
                }
            } catch (e: Exception) {
                _error.value = e.message
            }
            _loading.value = false
        }
    }
}