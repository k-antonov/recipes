package com.example.recipes.presentation.previews

import com.example.recipes.domain.previews.PreviewDomain
import com.example.recipes.presentation.core.ImageDownloader
import com.example.recipes.presentation.core.adapter.ClickableItemAdapter

class PreviewsAdapter(
    onItemClicked: (selectedItem: PreviewDomain) -> Unit
) : ClickableItemAdapter<PreviewDomain>(onItemClicked) {

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        ImageDownloader.load(holder.image, item.imageUrl)
        holder.title.text = item.name
    }

}