package com.ranatalal.tmdbexample.views.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ranatalal.tmdbexample.R
import com.ranatalal.tmdbexample.databinding.MovieListItemBinding
import com.ranatalal.tmdbexample.utils.MovieListUtilCallBack
import com.ranatalal.tmdbexample.views.models.MovieListResponseModel
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    Glide.with(view.context).load(url)
        .placeholder(R.drawable.placeholder).error(R.drawable.placeholder)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(view)
}

class MovieListAdapter(val context: Context) :
    PagingDataAdapter<MovieListResponseModel, RecyclerView.ViewHolder>(
        MovieListUtilCallBack()
    )   {
    var listener: ((item: MovieListResponseModel, position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: MovieListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.movie_list_item, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder: ViewHolder = holder as ViewHolder

        getItem(position)?.let {
            viewHolder.binding.model = it
            Log.d("MYTAG", "onBindViewHolder: ${it.releaseDate}")
            if (!it.releaseDate.isNullOrBlank()&&checkMovieDate(it.releaseDate!!)){
                viewHolder.binding.dateView.setTextColor(ContextCompat.getColor(context,R.color.red))
            }else{
                viewHolder.binding.dateView.setTextColor(ContextCompat.getColor(context,R.color.black))
            }

        }

        holder.binding.root.setOnClickListener {
            getItem(position)?.let {
                listener?.invoke(it, position)
            }
        }

    }

    private fun checkMovieDate(date: String): Boolean {
        val movieDateCalcender: Calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH)
        movieDateCalcender.setTime(sdf.parse(date))
        val currentCalcender: Calendar = Calendar.getInstance()
        return currentCalcender.get(Calendar.YEAR)==movieDateCalcender.get(Calendar.YEAR)


    }


    class ViewHolder(var binding: MovieListItemBinding) :
        RecyclerView.ViewHolder(binding.root)


}