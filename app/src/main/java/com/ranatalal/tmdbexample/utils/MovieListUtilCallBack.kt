package com.ranatalal.tmdbexample.utils

import androidx.recyclerview.widget.DiffUtil
import com.ranatalal.tmdbexample.views.models.MovieListResponseModel

class MovieListUtilCallBack : DiffUtil.ItemCallback<MovieListResponseModel>() {
    override fun areItemsTheSame(oldItem: MovieListResponseModel, newItem: MovieListResponseModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieListResponseModel, newItem: MovieListResponseModel): Boolean {
        return oldItem.title == newItem.title
                && oldItem.adult == newItem.adult
                && oldItem.backdropPath == newItem.backdropPath
                && oldItem.genreIds == newItem.genreIds
                && oldItem.originalLanguage == newItem.originalLanguage
                && oldItem.originalTitle == newItem.originalTitle
                && oldItem.overview == newItem.overview
                && oldItem.popularity == newItem.popularity
                && oldItem.posterPath == newItem.posterPath
                && oldItem.releaseDate == newItem.releaseDate
                && oldItem.title == newItem.title
                && oldItem.video == newItem.video
                && oldItem.voteAverage == newItem.voteAverage
                && oldItem.voteCount == newItem.voteCount
    }

}