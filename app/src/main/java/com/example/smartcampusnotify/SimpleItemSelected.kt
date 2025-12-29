package com.example.smartcampusnotify

import android.view.View
import android.widget.AdapterView

class SimpleItemSelected(
    private val onSelected: () -> Unit
) : AdapterView.OnItemSelectedListener {

    override fun onItemSelected(
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long
    ) {
        onSelected()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}
