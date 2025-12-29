package com.example.smartcampusnotify.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartcampusnotify.R
import com.example.smartcampusnotify.models.NotificationItem
import kotlin.math.max

class NotificationAdapter(
    private var items: List<NotificationItem>
) : RecyclerView.Adapter<NotificationAdapter.VH>() {

    fun update(newItems: List<NotificationItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val imgType: ImageView = v.findViewById(R.id.imgType)
        val txtTitle: TextView = v.findViewById(R.id.txtTitle)
        val txtDesc: TextView = v.findViewById(R.id.txtDesc)
        val txtType: TextView = v.findViewById(R.id.txtType)
        val txtTime: TextView = v.findViewById(R.id.txtTime)
        val txtStatus: TextView = v.findViewById(R.id.txtStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.activity_notification_item, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(h: VH, position: Int) {
        val n = items[position]
        h.txtTitle.text = n.title
        h.txtDesc.text = n.description
        h.txtType.text = "Tür: ${n.type}"
        h.txtStatus.text = n.status
        h.txtTime.text = timeAgo(n.createdAt)

        h.imgType.setImageResource(
            when (n.type.lowercase()) {
                "elektrik" -> android.R.drawable.ic_dialog_alert
                "su" -> android.R.drawable.ic_menu_compass
                "güvenlik" -> android.R.drawable.ic_lock_lock
                else -> android.R.drawable.ic_dialog_info
            }
        )
    }

    override fun getItemCount(): Int = items.size

    private fun timeAgo(tsMillis: Long): String {
        if (tsMillis <= 0L) return ""
        val diff = max(0L, System.currentTimeMillis() - tsMillis)
        val sec = diff / 1000
        val min = sec / 60
        val hour = min / 60
        val day = hour / 24
        return when {
            day > 0 -> "${day} gün önce"
            hour > 0 -> "${hour} saat önce"
            min > 0 -> "${min} dk önce"
            else -> "${sec} sn önce"
        }
    }
}
