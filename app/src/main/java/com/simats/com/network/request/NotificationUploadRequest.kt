package com.simats.com.network.request

data class NotificationUploadRequest(
    val username: String,
    val app_source: String,
    val sender: String,
    val message_content: String,
    val timestamp: String
)
