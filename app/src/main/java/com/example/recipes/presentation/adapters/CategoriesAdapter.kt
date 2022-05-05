package com.example.recipes.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes.R
import com.example.recipes.domain.entities.CategoryDomain
import com.example.recipes.presentation.ImageDownloader

// возможно стоит сделать дженерик адаптер
class CategoriesAdapter(
    private val categories: List<CategoryDomain>
) : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val image: ImageView = itemView.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_list_item, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        ImageDownloader.load(holder.image, category.imageUrl)
        holder.title.text = category.title
    }

    override fun getItemCount() = categories.size
}