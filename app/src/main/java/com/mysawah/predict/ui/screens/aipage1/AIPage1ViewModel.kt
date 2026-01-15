package com.mysawah.predict.ui.screens.aipage1

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mysawah.predict.data.remote.ApiConfig
import com.mysawah.predict.domain.model.RFPredictRequest
import com.mysawah.predict.domain.model.ChatMessage
import kotlinx.coroutines.launch

class AIPage1ViewModel : ViewModel() {

    var input by mutableStateOf("")
    var messages = mutableStateListOf<ChatMessage>()

    init {
        messages.add(
            ChatMessage(
                """
Halo, aku Asisten AI MySawah 👋
Silahkan masukkan 7 input data kondisi tanah Anda untuk rekomendasi Tanaman Utama yang cocok pada lahan Anda.

N – Kandungan Nitrogen dalam Tanah (dalam mg/kg)
P – Kandungan Fosfor dalam Tanah (dalam mg/kg)
K – Kandungan Kalium dalam Tanah (dalam mg/kg)
Suhu – Suhu Rata-rata dalam °C
Kelembaban – Kelembaban Relatif Rata-rata dalam %
ph – Nilai pH Tanah
Curah Hujan – Curah Hujan dalam mm

Contoh Input: 7.2, 0.13, 0.11, 21, 0.076, 25, 78
                """.trimIndent(),
                true
            )
        )
    }

    private fun parseInput(input: String): List<Float>? {
        return try {
            val values = input
                .split(",")
                .map { it.trim() }
                .filter { it.isNotEmpty() }
                .map { it.toFloat() }

            if (values.size == 7) values else null
        } catch (_: Exception) {
            null
        }
    }

    fun sendMessage() {
        if (input.isBlank()) return

        val userInput = input
        messages.add(ChatMessage(userInput, false))
        input = ""

        val values = parseInput(userInput)
        if (values == null) {
            messages.add(
                ChatMessage(
                    "Format salah. Masukkan 7 angka.\n" +
                            "Contoh Input: 7.2, 0.13, 0.11, 21, 0.076, 25, 78",
                    true
                )
            )
            return
        }

        viewModelScope.launch {
            try {
                val request = RFPredictRequest(
                    N = values[0],
                    P = values[1],
                    K = values[2],
                    temperature = values[3],
                    humidity = values[4],
                    ph = values[5],
                    rainfall = values[6]
                )

                val response =
                    ApiConfig.fastApi.predictRandomForest(request)

                if (response.isSuccessful) {
                    val result = response.body()
                    messages.add(
                        ChatMessage(
                            "Tanaman utama yang direkomendasikan berdasarkan data tanah Anda adalah ${result?.prediction}",
                            true
                        )
                    )
                } else {
                    messages.add(
                        ChatMessage(
                            "Gagal memproses data. Silakan coba kembali.",
                            true
                        )
                    )
                }

            } catch (e: Exception) {
                messages.add(
                    ChatMessage(
                        "Terjadi kesalahan: ${e.localizedMessage}",
                        true
                    )
                )
            }
        }
    }
}
