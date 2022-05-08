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
import com.example.recipes.domain.entities.PreviewDomain
import com.example.recipes.presentation.ImageDownloader

class PreviewAdapter<T>(
    private val items: List<T>,
    private val onItemClicked: (position: Int) -> Unit
) : RecyclerView.Adapter<PreviewAdapter.ItemViewHolder>() {

    class ItemViewHolder(
        itemView: View,
        private val onItemClicked: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val title: TextView = itemView.findViewById(R.id.title)
        val image: ImageView = itemView.findViewById(R.id.image)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            onItemClicked(adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_list_item, parent, false)
        return ItemViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        // todo fix DRY violation
        when (val item = items[position]) {
            is CategoryDomain -> {
                ImageDownloader.load(holder.image, item.imageUrl)
                holder.title.text = item.name
            }
            is CuisineDomain -> {
                ImageDownloader.load(holder.image, item.imageUrl)
                holder.title.text = item.name
            }
            is PreviewDomain -> {
                ImageDownloader.load(holder.image, item.imageUrl)
                holder.title.text = item.name
            }
        }
    }

    override fun getItemCount() = items.size
}