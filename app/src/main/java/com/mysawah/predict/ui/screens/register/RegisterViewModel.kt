package com.mysawah.predict.ui.screens.register

import android.content.Context // <--- Import Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mysawah.predict.data.local.SaveToken // <--- Import TokenManager
import com.mysawah.predict.data.repository.AuthRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RegisterViewModel : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _success = MutableStateFlow<String?>(null)
    val success = _success.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun register(name: String, email: String, password: String, context: Context) {
        viewModelScope.launch {
            _loading.value = true

            try {
                val response = AuthRepository.register(name, email, password)

                if (response.isSuccessful && response.body() != null) {

                    val token = response.body()!!.token
                    if (!token.isNullOrEmpty()) {
                        SaveToken.saveToken(context, token)
                    }

                    _success.value = response.body()!!.message
                } else {
                    _error.value = response.errorBody()?.string()
                        ?: "Registrasi gagal!"
                }

            } catch (e: Exception) {
                _error.value = e.message ?: "Terjadi kesalahan."
            }

            _loading.value = false
        }
    }

    fun clearError() { _error.value = null }
    fun clearSuccess() { _success.value = null }
    fun setError(msg: String) { _error.value = msg }
}