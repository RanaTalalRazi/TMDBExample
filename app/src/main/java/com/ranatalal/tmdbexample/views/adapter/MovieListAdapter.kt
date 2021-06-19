package com.ranatalal.tmdbexample.views.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ranatalal.tmdbexample.R
import com.ranatalal.tmdbexample.databinding.MovieListItemBinding
import com.ranatalal.tmdbexample.views.models.MovieResponseModel

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    Glide.with(view.context).load(url)
        .thumbnail(0.01f)
        .placeholder(R.drawable.placeholder).error(R.drawable.placeholder)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(view)
}

class MovieListAdapter :
    BaseRecyclerViewAdapter<MovieResponseModel, MovieListItemBinding>()  {
    override fun getLayout()= R.layout.movie_list_item

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<MovieListItemBinding>,
        position: Int
    ) {
        holder.binding.model = items[position]
        holder.binding.executePendingBindings()

    }
}