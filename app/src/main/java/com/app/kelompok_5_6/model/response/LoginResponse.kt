package com.app.kelompok_5_6.model.response


data class LoginResponse(
    val code: Int,
    val message: String,
    val data : UserLogin,
    val token: String
)

data class UserLogin(
    val uuid: String,
    val fullname: String
)