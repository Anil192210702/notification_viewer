package com.simats.com.network.request

import com.google.gson.annotations.SerializedName

data class ClearDataRequest(
    @SerializedName("admin_username") val admin_username: String,
    @SerializedName("data_type") val data_type: String
)
