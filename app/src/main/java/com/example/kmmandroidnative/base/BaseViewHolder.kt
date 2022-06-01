package com.example.kmmandroidnative.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class BaseViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    fun <T : View?> findViewById(id: Int): T = itemView.findViewById(id)
}
