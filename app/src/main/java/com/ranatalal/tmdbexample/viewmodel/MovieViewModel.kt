package com.ranatalal.tmdbexample.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.*
import androidx.paging.*
import com.ranatalal.tmdbexample.dataSource.MovieListDataSource
import com.ranatalal.tmdbexample.helper.SingleLiveEvent
import com.ranatalal.tmdbexample.networks.repositry.MovieRepositry
import com.ranatalal.tmdbexample.views.models.MovieListResponseModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

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


    var movieListDataResponse: Flow<PagingData<MovieListResponseModel>>? = null

    var searchProductListDataResponse: Flow<PagingData<MovieListResponseModel>>? = null


    val hasSearchTextClearClick = SingleLiveEvent<Void>()
    val hasSearchClick = SingleLiveEvent<Void>()


    init {
        if (!savedStateHandle.contains(KEY_SUBREDDIT)) {
            savedStateHandle.set(KEY_SUBREDDIT, DEFAULT_SUBREDDIT)

        }
    }

    private val clearListCh = Channel<Unit>(Channel.CONFLATED)

    @FlowPreview
    @ExperimentalCoroutinesApi
    @SuppressLint("CheckResult")

    fun getMovieList() {
        /*  movieListDataResponse = flowOf(
              clearListCh.receiveAsFlow().map { PagingData.empty<MovieListResponseModel>() },
              savedStateHandle.getLiveData<Int>(KEY_TAB_CHANGE)
                  .asFlow()
                  .flatMapLatest {
                      movieRepositry.getMovieList()
                  }
                  .cachedIn(viewModelScope)
          ).flattenMerge(2)*/


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