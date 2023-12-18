package com.example.kotlinfinal.data.entity

data class JwtUser(
    val id: Long?,
    var username: String?,
    var email: String?,
    var firstName: String?,
    var lastName: String?,
    var gender: String?,
    val image: String?,
    val token: String?
)
