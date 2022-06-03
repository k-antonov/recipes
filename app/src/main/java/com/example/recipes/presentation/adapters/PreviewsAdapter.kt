package com.example.recipes.presentation.adapters

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.example.recipes.domain.entities.PreviewDomain
import com.example.recipes.presentation.utils.ImageDownloader

private const val KEY_ID = "id"
private const val KEY_NAME = "name"
private const val KEY_IMAGE_URL = "image_url"


class PreviewsAdapter : ClickableItemAdapter<PreviewDomain>() {

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        ImageDownloader.load(holder.image, item.imageUrl)
        holder.title.text = item.name
    }

//    override fun reload(newList: List<PreviewDomain>) {
//        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
//            DiffUtilCallbackPreviews(oldList = items, newList = newList)
//        )
//
//        val newListNames = newList.map { it.name }
//        Log.d("PreviewsAdapter", "newList=${newListNames}")
//
//        items = newList
//
//        diffResult.dispatchUpdatesTo(this)
//    }

    override fun reload(newList: List<PreviewDomain>) {
        items = newList
        notifyDataSetChanged()
    }

}

class DiffUtilCallbackPreviews(
    private val oldList: List<PreviewDomain>,
    private val newList: List<PreviewDomain>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        Log.d(
            "DiffUtils",
            "same Items? ${oldItem.name} == ${newItem.name} => ${oldItem.id == newItem.id}"
        )
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        Log.d("DiffUtils", "same contents? $oldItem == $newItem => ${oldItem == newItem}")
        return oldItem == newItem
    }

    // наверное можно удалить, не вызывается
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[oldItemPosition]

        Log.d("DiffUtils", "getChangePayload called: oldItem=$oldItem, newItem=$newItem")

        val diff = Bundle()
        Log.d(
            "DiffUtils",
            "getChangePayload called: oldItem.name=${oldItem.name}, newItem.name=${newItem.name}"
        )
        if (oldItem.id != newItem.id) {
            diff.putLong(KEY_ID, newItem.id)
        }
        // возможно следующие проверки уже не нужны
        if (oldItem.name != newItem.name) {
            diff.putString(KEY_NAME, newItem.name)
        }
        if (oldItem.imageUrl != newItem.imageUrl) {
            diff.putString(KEY_IMAGE_URL, newItem.imageUrl)
        }

        Log.d("DiffUtils", "diff=$diff")

        return if (diff.size() == 0) {
            super.getChangePayload(oldItemPosition, newItemPosition)
        } else diff
    }
}