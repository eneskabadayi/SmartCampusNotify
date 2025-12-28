package com.example.smartcampusnotify.data

package com.example.smartcampusnotify.data

import com.google.firebase.Timestamp

data class AppUser(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val unit: String = "",
    val role: String = "User" // User / Admin
)

data class CampusNotification(
    val id: String = "",
    val type: String = "Genel",          // Örn: "Acil", "Bakım", "Güvenlik"...
    val title: String = "",
    val description: String = "",
    val status: String = "Açık",         // Açık / İnceleniyor / Çözüldü
    val createdAt: Timestamp? = null,
    val createdBy: String = "",          // uid
    val unit: String = "",               // bildirim birimi
    val lat: Double? = null,
    val lng: Double? = null,
    val photoUrl: String? = null
)

data class EmergencyAnnouncement(
    val id: String = "",
    val title: String = "",
    val message: String = "",
    val createdAt: Timestamp? = null,
    val createdBy: String = ""
)
