package com.example.smartcampusnotify

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AdminPanelActivity : AppCompatActivity() {

    private val auth by lazy { FirebaseAuth.getInstance() }
    private val db by lazy { FirebaseFirestore.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_panel)

        val etTitle = findViewById<EditText>(R.id.etNotifTitle)
        val etMessage = findViewById<EditText>(R.id.etNotifMessage)
        val etTargetUnit = findViewById<EditText>(R.id.etTargetUnit)
        val btnSend = findViewById<Button>(R.id.btnSendNotif)

        findViewById<Button>(R.id.btnLogout).setOnClickListener {
            auth.signOut()
            val i = Intent(this, LoginActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
            finish()
        }

        btnSend.setOnClickListener {
            val title = etTitle.text.toString().trim()
            val message = etMessage.text.toString().trim()
            val target = etTargetUnit.text.toString().trim().ifEmpty { "ALL" }

            if (title.isEmpty() || message.isEmpty()) {
                Toast.makeText(this, "Başlık ve mesaj zorunlu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val uid = auth.currentUser?.uid
            if (uid == null) {
                Toast.makeText(this, "Oturum yok", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            btnSend.isEnabled = false
            btnSend.text = "Kaydediliyor..."

            val data = hashMapOf(
                "title" to title,
                "message" to message,
                "targetUnit" to target,
                "createdAt" to Timestamp.now(),
                "createdBy" to uid
            )

            db.collection("notifications")
                .add(data)
                .addOnSuccessListener {
                    Toast.makeText(this, "Bildirim kaydedildi", Toast.LENGTH_SHORT).show()
                    etTitle.setText("")
                    etMessage.setText("")
                    // targetUnit kalsın, sürekli aynı birime atıyorsan kolaylık
                    btnSend.isEnabled = true
                    btnSend.text = "Bildirimi Kaydet"
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, e.localizedMessage ?: "Hata", Toast.LENGTH_LONG).show()
                    btnSend.isEnabled = true
                    btnSend.text = "Bildirimi Kaydet"
                }
        }
    }
}
