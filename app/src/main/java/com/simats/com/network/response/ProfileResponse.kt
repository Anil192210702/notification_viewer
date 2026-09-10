package com.simats.com.network.response

data class ProfileResponse(
    val full_name: String,
    val phone_number: String,
    val email: String,
    val profile_image_base64: String
)
