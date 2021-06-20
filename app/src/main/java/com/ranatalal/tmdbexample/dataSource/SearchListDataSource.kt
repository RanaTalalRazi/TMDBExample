package com.ranatalal.tmdbexample.dataSource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ranatalal.tmdbexample.networks.RetrofitFactory
import com.ranatalal.tmdbexample.utils.ConstUtils
import com.ranatalal.tmdbexample.utils.CustomException
import com.ranatalal.tmdbexample.views.models.MovieListResponseModel
import retrofit2.HttpException
import java.io.IOException

class SearchListDataSource(val searchKeyword:String) : PagingSource<Int, MovieListResponseModel>() {

    override val keyReuseSupported: Boolean = true

    override fun getRefreshKey(state: PagingState<Int, MovieListResponseModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieListResponseModel> {
        return try {

            val data = RetrofitFactory.getClient().getSearchList(
                searchKeyword,
                ConstUtils.API_KEY,
                page = params.key ?: 1,
            )


            if (data.isSuccessful) {
                LoadResult.Page(
                    data = data.body()?.results!!,
                    prevKey = null,
                    nextKey = data.body()?.page!!.plus(1)
                )
            } else {
                return LoadResult.Error(CustomException("Error"))
            }
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

}