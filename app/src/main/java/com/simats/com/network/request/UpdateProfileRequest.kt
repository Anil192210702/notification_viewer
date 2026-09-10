package com.simats.com.network.request

data class UpdateProfileRequest(
    val username: String,
    val full_name: String? = null,
    val phone_number: String? = null,
    val profile_image_base64: String? = null
)
