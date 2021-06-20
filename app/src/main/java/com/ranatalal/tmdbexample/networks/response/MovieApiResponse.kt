package  com.ranatalal.tmdbexample.networks.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ranatalal.tmdbexample.views.models.MovieListResponseModel
import java.io.Serializable

class MovieApiResponse : Serializable {

    @SerializedName("page")
    @Expose
    var page:Int? = null

    @SerializedName("results")
    @Expose
    var results: List<MovieListResponseModel>? =null

    @SerializedName("total_pages")
    @Expose
    var totalPages: Int  = 0

    @SerializedName("total_results")
    @Expose
    var totalResults: Int = 0



}
