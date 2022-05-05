package com.example.recipes.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes.R
import com.example.recipes.domain.entities.CategoryDomain
import com.example.recipes.domain.entities.CuisineDomain
import com.example.recipes.presentation.ImageDownloader

class SearchTabAdapter<T>(
    private val items: List<T>
) : RecyclerView.Adapter<SearchTabAdapter.ItemViewHolder>() {

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val image: ImageView = itemView.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_list_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        // todo fix DRY violation
        when (val item = items[position]) {
            is CategoryDomain -> {
                ImageDownloader.load(holder.image, item.imageUrl)
                holder.title.text = item.title
            }
            is CuisineDomain -> {
                ImageDownloader.load(holder.image, item.imageUrl)
                holder.title.text = item.title
            }
        }
    }

    override fun getItemCount() = items.size
}