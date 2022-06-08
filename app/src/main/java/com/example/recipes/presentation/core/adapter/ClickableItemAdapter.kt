package com.example.recipes.presentation.core.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes.R
import com.example.recipes.domain.core.DomainEntity

abstract class ClickableItemAdapter<T>(
    private val onItemClicked: (selectedItem: T) -> Unit
) : RecyclerView.Adapter<ClickableItemAdapter<T>.ItemViewHolder>() {

    protected var items = listOf<T>()

    inner class ItemViewHolder(
        itemView: View,
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val title: TextView = itemView.findViewById(R.id.title)
        val image: ImageView = itemView.findViewById(R.id.image)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            onItemClicked(items[adapterPosition])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_list_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount() = items.size

    open fun reload(newList: List<T>) {
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            DiffUtilCallback(oldList = items, newList = newList)
        )

        items = newList

        diffResult.dispatchUpdatesTo(this)
    }
}

class DiffUtilCallback<T>(
    private val oldList: List<T>,
    private val newList: List<T>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        if (oldItem is DomainEntity && newItem is DomainEntity) {
            return oldItem.id == newItem.id
        } else {
            throw ClassNotFoundException("Unknown class")
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }

}