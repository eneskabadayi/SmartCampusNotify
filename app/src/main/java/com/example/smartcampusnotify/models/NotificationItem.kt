package com.example.smartcampusnotify.models

data class NotificationItem(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val type: String = "",
    val status: String = "Açık",
    val unit: String = "",
    val createdAt: Long = 0L,
    val createdBy: String = "",
    val creatorName: String = "",
    val lat: Double = 0.0,
    val lng: Double = 0.0
)
