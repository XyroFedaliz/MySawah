package com.mysawah.predict.ui.screens.aipage2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mysawah.predict.data.remote.ApiConfig
import com.mysawah.predict.domain.model.CBPredictRequest
import kotlinx.coroutines.launch
import androidx.compose.runtime.*
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight

class AIPage2ViewModel : ViewModel() {

    var mainPlant by mutableStateOf("")
        private set

    var companionPlant by mutableStateOf("")
        private set

    var mainPlantType by mutableStateOf("")
        private set

    var result by mutableStateOf<AnnotatedString?>(null)
        private set

    fun updateMainPlant(value: String) {
        mainPlant = value
    }

    fun updateCompanionPlant(value: String) {
        companionPlant = value
    }

    fun updateMainPlantType(value: String) {
        mainPlantType = value
    }

    fun checkResult() {
        if (
            mainPlant.isBlank() ||
            companionPlant.isBlank() ||
            mainPlantType.isBlank()
        ) {
            result = buildAnnotatedString {
                append("Semua kolom harus diisi terlebih dahulu.")
            }
            return
        }

        viewModelScope.launch {
            try {
                val request = CBPredictRequest(
                    sourceNode = mainPlant,
                    destinationNode = companionPlant,
                    sourceType = mainPlantType
                )

                val response =
                    ApiConfig.fastApi.predictCatBoost(request)

                if (response.isSuccessful) {
                    val label = response.body()?.prediction ?: "tidak diketahui"

                    val kalimatPrediksi = when (label) {
                        "dibantu_oleh" -> "sangat membantu pertumbuhan"
                        "membantu" -> "membantu pertumbuhan"
                        "hindari" -> "sebaiknya dihindari karena dapat menghambat pertumbuhan"
                        else -> label
                    }

                    result = buildAnnotatedString {
                        append("Berdasarkan hasil analisis Asisten AI MySawah, tanaman pendamping $companionPlant ")

                        withStyle(
                            style = SpanStyle(fontWeight = FontWeight.Bold)
                        ) {
                            append("$kalimatPrediksi ")
                        }

                        append("$mainPlant ($mainPlantType).")
                    }

                } else {
                    result = buildAnnotatedString {
                        append("Gagal mendapatkan hasil AI.")
                    }
                }

            } catch (e: Exception) {
                result = buildAnnotatedString {
                    append("Terjadi kesalahan: ${e.message}")
                }
            }
        }
    }

    fun reset() {
        mainPlant = ""
        companionPlant = ""
        mainPlantType = ""
        result = null
    }
}
