package com.example.recipes.presentation.core.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes.R

abstract class ClickableItemAdapter<T> : RecyclerView.Adapter<ClickableItemAdapter.ItemViewHolder>() {

    protected var items = listOf<T>()

    var onItemClicked: (position: Int) -> Unit = {}

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

    override fun getItemCount() = items.size

    abstract fun reload(newList: List<T>)
}