package com.sun.adsfinder01.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sun.adsfinder01.R
import com.sun.adsfinder01.data.model.Place
import com.sun.adsfinder01.databinding.ItemPostsBinding
import java.util.Objects

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private var data: List<Place> = ArrayList()

    fun updatePlaces(places: List<Place>) = when {
        data.isNullOrEmpty() -> {
            this.data = places
            notifyItemRangeInserted(0, places.size)
        }
        else -> {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return data.size
                }

                override fun getNewListSize(): Int {
                    return places.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return data.get(oldItemPosition).id == places.get(newItemPosition).id
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val place = places.get(newItemPosition)
                    val old = places.get(oldItemPosition)
                    return place.id == old.id && Objects.equals(place.id, place.id)
                }
            })
            this.data = places
            result.dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding: ItemPostsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_posts,
            parent,
            false
        )
        // Binding callback here
        return HomeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (data.isNullOrEmpty()) 0 else data.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class HomeViewHolder(private val binding: ItemPostsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(place: Place) {
        }
    }
}
