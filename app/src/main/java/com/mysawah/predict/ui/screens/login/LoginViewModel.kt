package com.mysawah.predict.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mysawah.predict.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _success = MutableStateFlow<String?>(null)
    val success = _success.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun login(email: String, password: String, context: android.content.Context) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = AuthRepository.login(email, password)
                if (response.isSuccessful && response.body() != null) {
                    val token = response.body()!!.token
                    if (!token.isNullOrEmpty()) {
                        com.mysawah.predict.data.local.SaveToken.saveToken(context, token)
                        android.util.Log.d("CEK_TOKEN", "Token berhasil disimpan: $token")
                        val checkToken = com.mysawah.predict.data.local.SaveToken.getToken(context)
                        android.util.Log.d("CEK_TOKEN", "Isi Token di Memory: $checkToken")
                    }

                    _success.value = response.body()!!.message
                } else {
                    _error.value = response.body()?.message
                        ?: response.errorBody()?.string()
                                ?: "Login gagal!"
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Terjadi kesalahan."
            }
            _loading.value = false
        }
    }
    fun clearError() {
        _error.value = null
    }

    fun resetSuccess() {
        _success.value = null
    }

    fun setError(msg: String) {
        _error.value = msg
    }
}
