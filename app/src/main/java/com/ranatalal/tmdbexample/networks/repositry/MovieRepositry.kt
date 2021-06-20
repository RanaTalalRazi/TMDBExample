package com.ranatalal.tmdbexample.networks.repositry

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.ranatalal.tmdbexample.dataSource.MovieListDataSource
import com.ranatalal.tmdbexample.dataSource.SearchListDataSource
import com.ranatalal.tmdbexample.networks.RetrofitFactory
import com.ranatalal.tmdbexample.utils.ConstUtils
import org.koin.dsl.module

val movieRepositryModule = module {
    single { MovieRepositry() }
}

class MovieRepositry {

    fun client() = RetrofitFactory.getClient()

    suspend fun getMovieDetail(id:Int) = client().getMovieDetail(id,ConstUtils.API_KEY)

    suspend fun getSearchList(searchKeyword:String) = Pager(
        PagingConfig(pageSize =10 , enablePlaceholders = false)
    ) {
        SearchListDataSource(searchKeyword)
    }.flow



}