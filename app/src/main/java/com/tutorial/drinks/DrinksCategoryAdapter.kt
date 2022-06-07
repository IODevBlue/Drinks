package com.tutorial.drinks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tutorial.drinks.databinding.CategoryViewholderBinding
import com.tutorial.drinks.network.DrinkCategory

class DrinksCategoryAdapter:ListAdapter<DrinkCategory,DrinksCategoryAdapter.ViewHolder>(DiffCallback) {
    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val binding = CategoryViewholderBinding.bind(view)
        fun bind(drinkCategory: DrinkCategory){
            binding.categoryText.text = drinkCategory.strCategory
            binding.root.setOnClickListener {
                listener?.let { it(drinkCategory) }
            }
        }

    }


    companion object DiffCallback : DiffUtil.ItemCallback<DrinkCategory>() {
        override fun areItemsTheSame(oldItem: DrinkCategory, newItem: DrinkCategory): Boolean {
            return oldItem.strCategory == newItem.strCategory
        }

        override fun areContentsTheSame(oldItem: DrinkCategory, newItem: DrinkCategory): Boolean {
           return oldItem == newItem
        }
    }

    private var listener:((DrinkCategory)->Unit)? = null

    fun adapterClickListener(listener:(DrinkCategory)->Unit){
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_viewholder,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pos = getItem(position)
        holder.bind(pos)
    }
}