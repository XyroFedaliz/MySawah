package com.mysawah.predict.ui.screens.account

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mysawah.predict.data.repository.AuthRepository
import com.mysawah.predict.domain.model.UserRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class AccountViewModel : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _imageSignature = MutableStateFlow(System.currentTimeMillis())
    val imageSignature = _imageSignature.asStateFlow()

    private val _userData = MutableStateFlow<UserRequest?>(null)
    val userData = _userData.asStateFlow()

    private val _showNameDialog = MutableStateFlow(false)
    val showNameDialog = _showNameDialog.asStateFlow()

    private val _showPhoneDialog = MutableStateFlow(false)
    val showPhoneDialog = _showPhoneDialog.asStateFlow()

    private val _showAddressDialog = MutableStateFlow(false)
    val showAddressDialog = _showAddressDialog.asStateFlow()

    private val _showPhotoDialog = MutableStateFlow(false)
    val showPhotoDialog = _showPhotoDialog.asStateFlow()

    fun openPhotoDialog() { _showPhotoDialog.value = true }
    fun closePhotoDialog() { _showPhotoDialog.value = false }

    fun fetchUserProfile(context: Context) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = AuthRepository.getUserProfile(context)
                if (response.isSuccessful && response.body() != null) {
                    _userData.value = response.body()!!.data
                }
            } catch (e: Exception) { e.printStackTrace() }
            _loading.value = false
        }
    }

    fun openNameDialog() { _showNameDialog.value = true }
    fun closeNameDialog() { _showNameDialog.value = false }
    fun openPhoneDialog() { _showPhoneDialog.value = true }
    fun closePhoneDialog() { _showPhoneDialog.value = false }
    fun openAddressDialog() { _showAddressDialog.value = true }
    fun closeAddressDialog() { _showAddressDialog.value = false }

    fun updateProfile(context: Context, newName: String? = null, newPhone: String? = null, newAddress: String? = null) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = AuthRepository.updateUser(context, newName, newPhone, newAddress)
                if (response.isSuccessful && response.body() != null) {
                    _userData.value = response.body()!!.data
                    closeNameDialog(); closePhoneDialog(); closeAddressDialog()
                    Toast.makeText(context, "Update Berhasil", Toast.LENGTH_SHORT).show()
                }
            } catch (_: Exception) { }
            _loading.value = false
        }
    }

    fun uploadPhoto(context: Context, uri: Uri) {
        viewModelScope.launch {
            _loading.value = true
            try {
                // Gunakan fungsi reduceFileImage agar ukuran file kecil
                val file = reduceFileImage(uriToFile(context, uri))

                val response = AuthRepository.uploadPhoto(context, file)

                if (response.isSuccessful && response.body() != null) {
                    _userData.value = response.body()!!.data
                    _imageSignature.value = System.currentTimeMillis()
                    Toast.makeText(context, "Foto berhasil diupload!", Toast.LENGTH_SHORT).show()
                } else {
                    // Cek error body untuk melihat pesan error dari server (HTML)
                    val errorMsg = response.errorBody()?.string()
                    Log.e("UploadError", errorMsg ?: "Unknown error")
                    Toast.makeText(context, "Gagal upload. Cek Logcat.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
            _loading.value = false
        }
    }

    fun deletePhoto(context: Context) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = AuthRepository.deletePhoto(context)
                if (response.isSuccessful) {
                    fetchUserProfile(context)
                    _imageSignature.value = System.currentTimeMillis()
                    Toast.makeText(context, "Foto berhasil dihapus", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Gagal hapus foto", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
            _loading.value = false
        }
    }

    // --- HELPER UNTUK MENGUBAH URI -> FILE ---
    private fun uriToFile(context: Context, uri: Uri): File {
        val myFile = File.createTempFile("temp_image", ".jpg", context.cacheDir)
        val inputStream = context.contentResolver.openInputStream(uri) as InputStream
        val outputStream = FileOutputStream(myFile)
        val buffer = ByteArray(1024)
        var length: Int
        while (inputStream.read(buffer).also { length = it } > 0) outputStream.write(buffer, 0, length)
        outputStream.close()
        inputStream.close()
        return myFile
    }

    private fun reduceFileImage(file: File): File {
        val bitmap = BitmapFactory.decodeFile(file.path)
        var compressQuality = 100
        var streamLength: Int
        do {
            val bmpStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
            val bmpPicByteArray = bmpStream.toByteArray()
            streamLength = bmpPicByteArray.size
            compressQuality -= 5
        } while (streamLength > 1000000)

        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
        return file
    }
}