package com.ranatalal.tmdbexample.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.*
import androidx.paging.*
import com.ranatalal.tmdbexample.dataSource.MovieListDataSource
import com.ranatalal.tmdbexample.helper.Resource
import com.ranatalal.tmdbexample.helper.SingleLiveEvent
import com.ranatalal.tmdbexample.networks.repositry.MovieRepositry
import com.ranatalal.tmdbexample.utils.ErrorUtils
import com.ranatalal.tmdbexample.views.models.ErrorModel
import com.ranatalal.tmdbexample.views.models.MovieListDetailResponseModel
import com.ranatalal.tmdbexample.views.models.MovieListResponseModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.lang.Exception
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

val movieViewModelModule = module {
    viewModel { (handle: SavedStateHandle) -> MovieViewModel(handle, get()) }
}

class MovieViewModel(
    val savedStateHandle: SavedStateHandle,
    val movieRepositry: MovieRepositry,
) : ViewModel() {

    companion object {
        const val KEY_SUBREDDIT = "subreddit"
        const val DEFAULT_SUBREDDIT = ""
    }

    val getMovieDetailDataResponse = SingleLiveEvent<Resource<MovieListDetailResponseModel>>()


    var movieListDataResponse: Flow<PagingData<MovieListResponseModel>>? = null

    var searchProductListDataResponse: Flow<PagingData<MovieListResponseModel>>? = null


    val hasSearchTextClearClick = SingleLiveEvent<Void>()
    val hasSearchClick = SingleLiveEvent<Void>()


    init {
        if (!savedStateHandle.contains(KEY_SUBREDDIT)) {
            savedStateHandle.set(KEY_SUBREDDIT, DEFAULT_SUBREDDIT)

        }
    }

    fun getMovieDetail(id:Int) {
        viewModelScope.launch {
            try {
                getMovieDetailDataResponse.value = Resource.loading()
                val response = movieRepositry.getMovieDetail(id)
                if (response.isSuccessful) {
                    getMovieDetailDataResponse.value = Resource.success(response.body())
                } else {
                    getMovieDetailDataResponse.value =
                        Resource.error(ErrorUtils.getErrorModelFromJson(response))
                }
            } catch (e: Exception) {
                if (e is UnknownHostException || e is TimeoutException) {
                    val model = ErrorModel()
                    model.isNoInternetError = true
                    getMovieDetailDataResponse.value =
                        Resource.error(model)
                } else {
                    val model = ErrorModel()
                    model.isNoInternetError = false
                    getMovieDetailDataResponse.value =
                        Resource.error(model)
                }
            }
        }
    }


    private val clearListCh = Channel<Unit>(Channel.CONFLATED)

    @FlowPreview
    @ExperimentalCoroutinesApi
    @SuppressLint("CheckResult")

    fun getMovieList() {
        movieListDataResponse = Pager(
            PagingConfig(pageSize = 10, enablePlaceholders = false)
        ) {
            MovieListDataSource()
        }.flow
            .cachedIn(viewModelScope)

    }

    fun getSearchedProducts() {
        searchProductListDataResponse = flowOf(
            clearListCh.receiveAsFlow().map { PagingData.empty<MovieListResponseModel>() },
            savedStateHandle.getLiveData<String>(KEY_SUBREDDIT)
                .asFlow()
                .flatMapLatest {
                    movieRepositry.getSearchList(it)
                }
                .cachedIn(viewModelScope)
        ).flattenMerge(2)
    }

    fun shouldShowSubreddit(subreddit: String) =
        savedStateHandle.get<String>(KEY_SUBREDDIT) != subreddit

    fun showSubreddit(subreddit: String) {
        if (!shouldShowSubreddit(subreddit)) return

        clearListCh.offer(Unit)

        savedStateHandle.set(KEY_SUBREDDIT, subreddit)
    }

    fun searchTextClearClick(){
        hasSearchTextClearClick.call()
    }
  fun searchClick(){
        hasSearchClick.call()
    }

}