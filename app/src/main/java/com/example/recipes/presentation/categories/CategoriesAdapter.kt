package com.example.recipes.presentation.categories

import com.example.recipes.domain.categories.CategoryDomain
import com.example.recipes.presentation.core.adapter.ClickableItemAdapter
import com.example.recipes.presentation.core.ImageDownloader

class CategoriesAdapter(
    onItemClicked: (selectedItem: CategoryDomain) -> Unit
) : ClickableItemAdapter<CategoryDomain>(onItemClicked) {

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