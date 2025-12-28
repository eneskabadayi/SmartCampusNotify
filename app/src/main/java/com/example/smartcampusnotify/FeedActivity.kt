package com.example.smartcampusnotify

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FeedActivity : AppCompatActivity() {

    private val auth by lazy { FirebaseAuth.getInstance() }
    private val db by lazy { FirebaseFirestore.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        val tvFeed = findViewById<TextView>(R.id.tvFeed)

        findViewById<Button>(R.id.btnLogout).setOnClickListener {
            auth.signOut()
            val i = Intent(this, LoginActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
            finish()
        }

        val uid = auth.currentUser?.uid
        if (uid == null) {
            Toast.makeText(this, "Oturum yok", Toast.LENGTH_SHORT).show()
            return
        }

        // önce kullanıcının unit bilgisini al
        db.collection("users").document(uid).get()
            .addOnSuccessListener { userDoc ->
                val unit = userDoc.getString("unit") ?: "ALL"
                loadNotifications(unit, tvFeed)
            }
            .addOnFailureListener {
                loadNotifications("ALL", tvFeed)
            }
    }

    private fun loadNotifications(unit: String, tvFeed: TextView) {
        // Basit mantık: ALL + kullanıcı birimi
        // Firestore'da "in" sorgusu kullanıyoruz
        db.collection("notifications")
            .whereIn("targetUnit", listOf("ALL", unit))
            .orderBy("createdAt", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { qs ->
                if (qs.isEmpty) {
                    tvFeed.text = "Henüz bildirim yok."
                    return@addOnSuccessListener
                }

                val sb = StringBuilder()
                for (doc in qs.documents) {
                    val title = doc.getString("title") ?: "-"
                    val message = doc.getString("message") ?: "-"
                    val target = doc.getString("targetUnit") ?: "ALL"
                    sb.append("• [$target] $title\n$message\n\n")
                }
                tvFeed.text = sb.toString()
            }
            .addOnFailureListener { e ->
                tvFeed.text = "Bildirimler yüklenemedi: ${e.localizedMessage}"
            }
    }
}
