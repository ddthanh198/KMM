package com.example.kmmandroidnative.base

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T>(val context: Context) : RecyclerView.Adapter<BaseViewHolder>() {
    abstract var layoutRes: Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = LayoutInflater.from(context).inflate(layoutRes, parent, false)
        return BaseViewHolder(view)
    }

    protected var items: MutableList<T> = mutableListOf()
    protected var callback: ItemClickListener<T>? = null

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    open fun updateList(newList: List<T>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }

    open fun addList(additionList: List<T>) {
        val oldCount = itemCount
        items.addAll(additionList)
        notifyItemRangeInserted(oldCount, additionList.size)
    }

    open fun insertItem(item: T) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    @SuppressLint("NotifyDataSetChanged")
    open fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    open fun updateItem(position: Int, newItem: T) {
        notifyItemChanged(position, newItem)
    }

    open fun getItemAt(position: Int): T {
        return items[position]
    }

    open fun setOnClickListener(callback: ItemClickListener<T>) {
        this.callback = callback
    }

    interface ItemClickListener<T> {
        fun onItemClick(position: Int, item: T)
    }
}
