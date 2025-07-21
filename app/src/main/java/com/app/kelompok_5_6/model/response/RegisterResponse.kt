package com.app.kelompok_5_6.model.response

data class RegisterResponse (
    val message: String,
    val user: UserRegister,
)

data class UserRegister(
    val id: String,
    val email: String,
    val username: String,
)