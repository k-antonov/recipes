package com.example.recipes.presentation.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

class ImageDownloader {
    companion object {
        fun load(imageView: ImageView, imageUrl: String) {
            Glide.with(imageView.context)
                .load(imageUrl)
                .centerCrop()
                .into(imageView)
        }
    }
}