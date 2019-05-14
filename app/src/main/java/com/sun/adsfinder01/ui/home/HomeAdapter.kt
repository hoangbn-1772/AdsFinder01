package com.sun.adsfinder01.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sun.adsfinder01.R
import com.sun.adsfinder01.data.model.Place
import com.sun.adsfinder01.databinding.ItemPostsBinding
import com.sun.adsfinder01.util.autoNotify

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private var data: List<Place> = ArrayList()

    fun updatePlaces(places: List<Place>) = when {
        data.isNullOrEmpty() -> {
            this.data = places
            notifyItemRangeInserted(0, places.size)
        }
        else -> {
            autoNotify(this.data, places) { o, n -> o.id == n.id }
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
