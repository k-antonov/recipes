package com.example.recipes.presentation.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes.R

// todo replace with map
class IngredientsAdapter : RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>() {

    private var ingredients = listOf<String>()
    private var measures = listOf<String>()

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

    fun reload(newIngredients: List<String>, newMeasures: List<String>) {
        ingredients = newIngredients
        measures = newMeasures
        notifyDataSetChanged()
    }

}