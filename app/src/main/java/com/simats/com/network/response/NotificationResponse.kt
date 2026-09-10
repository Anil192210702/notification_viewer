package com.simats.com.network.response

data class NotificationItem(
    val id: Int,
    val app_source: String,
    val sender: String,
    val message_content: String,
    val timestamp: String,
    val user__username: String
)

data class NotificationResponse(
    val notifications: List<NotificationItem>
)
