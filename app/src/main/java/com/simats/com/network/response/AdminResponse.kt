package com.simats.com.network.response

import com.google.gson.annotations.SerializedName

data class AdminResponse(
    @SerializedName("admin_username") val adminUsername: String,
    @SerializedName("bond") val bond: String
)
