package com.mysawah.predict.domain.model

data class UserRequest(
    val id: Int,
    val name: String,
    val email: String,
    val no_hp: String?,
    val alamat: String?,
    val foto_profil: String?
)
