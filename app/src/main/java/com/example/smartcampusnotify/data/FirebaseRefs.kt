package com.example.smartcampusnotify.data

package com.example.smartcampusnotify.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseRefs {
    val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    val db: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    fun currentUid(): String = auth.currentUser?.uid ?: ""

    // Collections
    fun usersCol() = db.collection("users")
    fun notificationsCol() = db.collection("notifications")
    fun emergenciesCol() = db.collection("emergencies")

    // User subcollections
    fun followedCol(uid: String = currentUid()) =
        usersCol().document(uid).collection("followed") // docs: { notificationId, followedAt }

    fun settingsDoc(uid: String = currentUid()) =
        usersCol().document(uid).collection("settings").document("notificationPrefs")
}
