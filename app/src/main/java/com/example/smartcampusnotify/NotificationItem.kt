package com.example.smartcampusnotify

import com.google.firebase.Timestamp

data class NotificationItem(
    val id: String = "",
    val title: String = "",
    val message: String = "",
    val targetUnit: String = "ALL",
    val createdAt: Timestamp? = null,
    val createdBy: String = ""
)
