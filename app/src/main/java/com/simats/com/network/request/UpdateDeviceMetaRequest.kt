package com.simats.com.network.request

data class UpdateDeviceMetaRequest(
    val username: String,
    val device_name: String,
    val battery_level: Int
)
