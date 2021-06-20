package  com.ranatalal.tmdbexample.networks.apiInterface

import com.ranatalal.tmdbexample.networks.response.MovieApiResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface RetrofitService {

    @GET("discover/movie?language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&with_watch_monetization_types=flatrate")
    suspend fun getMovieList(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int? = null,
    ): Response<MovieApiResponse>

    @GET("search/movie?language=en-US&include_adult=false")
    suspend fun getSearchList(
        @Query("query") searchKeyword: String,
        @Query( "api_key") apiKey: String,
        @Query("page") page: Int? = null,
    ): Response<MovieApiResponse>


}