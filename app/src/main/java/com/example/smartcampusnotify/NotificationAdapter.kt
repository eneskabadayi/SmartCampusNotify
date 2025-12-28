package com.example.smartcampusnotify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Locale

class NotificationAdapter(
    private val items: MutableList<NotificationItem>
) : RecyclerView.Adapter<NotificationAdapter.VH>() {

    private val df = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale("tr", "TR"))

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val tvTitle: TextView = v.findViewById(R.id.tvNotifTitle)
        val tvMeta: TextView = v.findViewById(R.id.tvNotifMeta)
        val tvMsg: TextView = v.findViewById(R.id.tvNotifMessage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_notification_item, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.tvTitle.text = item.title
        holder.tvMsg.text = item.message

        val dateStr = item.createdAt?.toDate()?.let { df.format(it) } ?: "-"
        holder.tvMeta.text = "${item.targetUnit} • $dateStr"
    }

    override fun getItemCount() = items.size

    fun setData(newItems: List<NotificationItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}
