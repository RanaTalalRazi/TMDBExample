package com.ranatalal.tmdbexample.networks.repositry

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.ranatalal.tmdbexample.dataSource.MovieListDataSource
import com.ranatalal.tmdbexample.dataSource.SearchListDataSource
import org.koin.dsl.module

val movieRepositryModule = module {
    single { MovieRepositry() }
}

class MovieRepositry {



   /* suspend fun getSearchedProducts(searchKeyword: String, pageSize: Int) = Pager(
        PagingConfig(pageSize = pageSize, enablePlaceholders = false)
    ) {
        SearchProductListDataSource(
            searchKeyword, pageSize, session
        )
    }.flow*/

    suspend fun getMovieList() = Pager(
        PagingConfig(pageSize =10 , enablePlaceholders = false)
    ) {
        MovieListDataSource()
    }.flow
    suspend fun getSearchList(searchKeyword:String) = Pager(
        PagingConfig(pageSize =10 , enablePlaceholders = false)
    ) {
        SearchListDataSource(searchKeyword)
    }.flow



}