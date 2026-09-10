package com.simats.com.network.request

data class SignUpRequest(
    val username: String,
    val email: String,
    val phone: String,
    val password: String
)
