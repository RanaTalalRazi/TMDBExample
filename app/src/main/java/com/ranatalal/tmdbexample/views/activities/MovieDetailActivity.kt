package com.ranatalal.tmdbexample.views.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ranatalal.tmdbexample.R
import com.ranatalal.tmdbexample.databinding.ActivityMovieDetailBinding
import com.ranatalal.tmdbexample.helper.Resource
import com.ranatalal.tmdbexample.utils.ConstUtils

@BindingAdapter("loadBackDropImage")
fun loadBackDropImage(view: ImageView, url: String?) {
    Glide.with(view.context).load(url)
        .placeholder(R.drawable.banner_placeholder).error(R.drawable.banner_placeholder)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(view)
}

class MovieDetailActivity : BaseActivity() {
    var binding: ActivityMovieDetailBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
        if (savedInstanceState == null) {
            initData()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        initData()
    }

    private fun initData() {

        movieViewModel.getMovieDetail(intent.getIntExtra(ConstUtils.ID, 0))

        movieViewModel.getMovieDetailDataResponse.observe(this, Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                    showLoading()

                }
                Resource.Status.SUCCESS -> {
                    hideLoading()
                    binding!!.model = it.data
                }
                Resource.Status.ERROR -> {
                    hideLoading()
                    uiHelper.showToast(
                        this,
                        it.error?.getErrorMessage(this) ?: ""
                    )

                }
            }
        })

    }
}