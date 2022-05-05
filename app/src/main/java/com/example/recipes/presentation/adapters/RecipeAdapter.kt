package com.example.recipes.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes.R
import com.example.recipes.domain.entities.RecipeFeedDomain
import com.example.recipes.presentation.ImageDownloader

class RecipeAdapter(
    private val recipeFeed: List<RecipeFeedDomain>,
    private val onItemClicked: (position: Int) -> Unit
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    companion object {
        private val TAG = RecipeAdapter::class.java.simpleName
    }

    class RecipeViewHolder(
        itemView: View,
        private val onItemClicked: (position: Int) -> Unit,
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val image: ImageView = itemView.findViewById(R.id.image)
        val title: TextView = itemView.findViewById(R.id.title)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            onItemClicked(adapterPosition)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_list_item, parent, false)
        Log.d(TAG, "onCreateViewHOlder")
        return RecipeViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipeFeed[position]

        Log.d(TAG, "$recipe")

        ImageDownloader.load(holder.image, recipe.imageUrl)
        holder.title.text = recipe.title
    }

    override fun getItemCount() = recipeFeed.size
}