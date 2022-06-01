package com.example.kmmandroidnative.dog_list

import android.content.Context
import android.media.Image
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.kmmandroidnative.R
import com.example.kmmandroidnative.base.BaseAdapter
import com.example.kmmandroidnative.base.BaseViewHolder

class DogImageListAdapter(context: Context) : BaseAdapter<String>(context) {
    override var layoutRes: Int = R.layout.item_dog_image

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val urlDogImage = items[position]

        holder.findViewById<ImageView>(R.id.imageDog).apply {
            Glide.with(holder.view).load(urlDogImage).into(this)
        }
    }
}