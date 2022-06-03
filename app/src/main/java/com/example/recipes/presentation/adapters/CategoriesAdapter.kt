package com.example.recipes.presentation.adapters

import com.example.recipes.domain.entities.CategoryDomain
import com.example.recipes.presentation.utils.ImageDownloader

class CategoriesAdapter : ClickableItemAdapter<CategoryDomain>() {

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        ImageDownloader.load(holder.image, item.imageUrl)
        holder.title.text = item.name
    }

    override fun reload(newList: List<CategoryDomain>) {
        items = newList
        notifyDataSetChanged()
    }
}