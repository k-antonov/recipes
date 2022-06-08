package com.example.recipes.presentation.cuisines

import com.example.recipes.domain.cuisines.CuisineDomain
import com.example.recipes.presentation.core.ImageDownloader
import com.example.recipes.presentation.core.adapter.ClickableItemAdapter

class CuisinesAdapter(
    onItemClicked: (selectedItem: CuisineDomain) -> Unit
) : ClickableItemAdapter<CuisineDomain>(onItemClicked) {

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