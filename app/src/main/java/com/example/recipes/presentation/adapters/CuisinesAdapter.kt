package com.example.recipes.presentation.adapters

import com.example.recipes.domain.entities.CuisineDomain
import com.example.recipes.presentation.utils.ImageDownloader

class CuisinesAdapter : ClickableItemAdapter<CuisineDomain>() {

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        ImageDownloader.load(holder.image, item.imageUrl)
        holder.title.text = item.name
    }

    override fun reload(newList: List<CuisineDomain>) {
        items = newList
        notifyDataSetChanged()
    }
}