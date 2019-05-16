package com.sun.adsfinder01.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sun.adsfinder01.R
import com.sun.adsfinder01.data.model.Place
import com.sun.adsfinder01.util.autoNotify
import kotlinx.android.synthetic.main.item_posts.imageLike
import kotlinx.android.synthetic.main.item_posts.view.imageLike
import kotlinx.android.synthetic.main.item_posts.view.imagePosts
import kotlinx.android.synthetic.main.item_posts.view.textDateCreate
import kotlinx.android.synthetic.main.item_posts.view.textPlaceSize
import kotlinx.android.synthetic.main.item_posts.view.textPosterType
import kotlinx.android.synthetic.main.item_posts.view.textWallType

class HomeAdapter(
    private var data: List<Place>,
    @NonNull private val itemOnClick: (place: Place) -> Unit,
    @NonNull private val savePlaceOnClick: (place: Place, status: Boolean) -> Unit
) :
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

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
        return HomeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_posts, parent, false))
    }

    override fun getItemCount(): Int {
        return if (data.isNullOrEmpty()) 0 else data.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(place: Place) {
            // Use for save place or remove place when user click
            var isSaved = false

            itemView.run {

                textWallType.text = place.wallType?.get(0)?.type
                textPosterType.text = place.posterType?.get(0)?.type
                textDateCreate.text = place.dateCreated
                showPlaceSize(place, textPlaceSize)
                showPlaceImage(place.imageUrl, imagePosts)

                setOnClickListener {
                    itemOnClick(place)
                }

                imageLike.setOnClickListener {
                    isSaved = onSavePlace(place, isSaved)
                }
            }
        }

        private fun showPlaceImage(uri: String?, imageView: ImageView) {
            Glide.with(imageView)
                .load(uri)
                .centerCrop()
                .placeholder(R.drawable.image_empty)
                .error(R.drawable.image_empty)
                .into(imageView)
        }

        private fun showPlaceSize(place: Place, textView: TextView) {
            val placeSize = StringBuilder()
            placeSize.append(itemView.resources.getString(R.string.place_size))
            placeSize.append("\t")
            placeSize.append(place.calculateAria())
            placeSize.append("\t")
            placeSize.append(itemView.resources.getString(R.string.place_unit))
            textView.text = placeSize.toString()
        }

        private fun onSavePlace(place: Place, isSaved: Boolean): Boolean = when {
            !isSaved -> {
                savePlaceOnClick(place, true)
                showIconSavePlace()
                true
            }
            else -> {
                savePlaceOnClick(place, false)
                showIconRemovePlace()
                false
            }
        }

        private fun showIconSavePlace(){
            itemView.imageLike?.setImageResource(R.drawable.ic_star_selected)
        }

        private fun showIconRemovePlace(){
            itemView.imageLike?.setImageResource(R.drawable.ic_star)
        }
    }
}
