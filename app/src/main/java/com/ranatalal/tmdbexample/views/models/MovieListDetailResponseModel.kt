package com.ranatalal.tmdbexample.views.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ranatalal.tmdbexample.utils.ConstUtils
import java.io.Serializable

class MovieListDetailResponseModel : Serializable {

    @SerializedName("adult")
    @Expose
    var adult: Boolean? = false

    @SerializedName("backdrop_path")
    @Expose
    var backdropPath: String? = ""

    @SerializedName("budget")
    @Expose
    var budget: Int? = null

    @SerializedName("id")
    @Expose
    var id: Int? = 0

    @SerializedName("imdb_id")
    @Expose
    var imdbId: String? = ""

    @SerializedName("homepage")
    @Expose
    var homepage: String? = ""

    @SerializedName("original_language")
    @Expose
    var originalLanguage: String? = ""

    @SerializedName("original_title")
    @Expose
    var originalTitle: String? = ""

    @SerializedName("overview")
    @Expose
    var overview: String? = ""

    @SerializedName("popularity")
    @Expose
    var popularity: Double? = 0.0

    @SerializedName("poster_path")
    @Expose
    var posterPath: String? = ""

    @SerializedName("release_date")
    @Expose
    var releaseDate: String? = ""

    @SerializedName("revenue")
    @Expose
    var revenue: String? = ""

    @SerializedName("runtime")
    @Expose
    var runtime: Int? = null

    @SerializedName("status")
    @Expose
    var status: String? = ""

    @SerializedName("tagline")
    @Expose
    var tagline: String? = ""

    @SerializedName("title")
    @Expose
    var title: String? = ""

    @SerializedName("video")
    @Expose
    var video: Boolean? = false

    @SerializedName("vote_average")
    @Expose
    var voteAverage: Float? = 0f

    @SerializedName("vote_count")
    @Expose
    var voteCount: Int? = 0

    fun getPoster()="${ConstUtils.IMAGE_BASE_URL}$posterPath"

    fun getBackDrop()="${ConstUtils.IMAGE_BASE_URL}$backdropPath"
}