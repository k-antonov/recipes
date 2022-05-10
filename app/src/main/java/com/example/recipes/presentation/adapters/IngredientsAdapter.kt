package com.example.recipes.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes.R

// todo replace with map
class IngredientsAdapter(
    private val ingredients: List<String>,
    private val measures: List<String>
) : RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>() {

    class IngredientsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ingredientAndMeasure: TextView = view.findViewById(R.id.ingredient_name_and_measure)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.ingredient_list_item, parent, false)
        return IngredientsViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        holder.ingredientAndMeasure.text = holder.itemView.context.getString(
            R.string.string_comma_string_placeholder,
            ingredients[position],
            measures[position]
        )
    }

    override fun getItemCount() = ingredients.size

}