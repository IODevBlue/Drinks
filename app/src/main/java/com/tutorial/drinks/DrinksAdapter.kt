package com.tutorial.drinks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.tutorial.drinks.databinding.AllDrinksViewholderBinding
import com.tutorial.drinks.network.Drink

class DrinksAdapter:ListAdapter<Drink,DrinksAdapter.ViewHolder>(DiffCallback) {
    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        private val binding = AllDrinksViewholderBinding.bind(view)
        fun bind(drink: Drink){
            binding.apply {
                drinkName.text = drink.strDrink
                drinkImg.load(drink.strDrinkThumb){
                    crossfade(true)
                    placeholder(R.drawable.ic_baseline_image_24)
                    error(R.drawable.ic_baseline_broken_image_24)
                }
            }
            binding.root.setOnClickListener {
                listener?.let { it(drink) }
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Drink>() {

        override fun areItemsTheSame(oldItem: Drink, newItem: Drink): Boolean {
            return oldItem.idDrink == newItem.idDrink
        }

        override fun areContentsTheSame(oldItem: Drink, newItem: Drink): Boolean {
            return oldItem == newItem
        }

    }

    private var listener:((Drink)->Unit)? = null

    fun adapterClickListener(listener:(Drink)->Unit){
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.all_drinks_viewholder,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pos = getItem(position)
        holder.bind(pos)
    }
}