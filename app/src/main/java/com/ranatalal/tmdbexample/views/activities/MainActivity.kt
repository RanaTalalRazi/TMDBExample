package com.ranatalal.tmdbexample.views.activities

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.ranatalal.tmdbexample.R
import com.ranatalal.tmdbexample.databinding.ActivityMainBinding
import com.ranatalal.tmdbexample.views.adapter.MovieListAdapter
import com.ranatalal.tmdbexample.views.adapters.MovieLoadStateAdapter
import com.ranatalal.tmdbexample.views.fragment.SearchFragment
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : BaseActivity() {
    private val movieListAdapter by lazy {
        MovieListAdapter(this)
    }

    var binding: ActivityMainBinding? = null

    @FlowPreview
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding!!.viewModel = movieViewModel
        initData()
    }

    @FlowPreview
    private fun initData() {

        movieViewModel.getMovieList()

        lifecycleScope.launchWhenCreated {
            movieListAdapter.loadStateFlow.collectLatest { loadStates ->
                if (loadStates.refresh is LoadState.Error) {
                    uiHelper.showLongToastInCenter(
                        this@MainActivity,
                        (loadStates.refresh as LoadState.Error).error.message
                            ?: ""
                    )
                }
            }
        }

        lifecycleScope.launch {
            movieViewModel.movieListDataResponse?.collectLatest {
                movieListAdapter.submitData(it)
            }
        }


        binding!!.movieList.adapter = movieListAdapter.withLoadStateHeaderAndFooter(
            header = MovieLoadStateAdapter(),
            footer = MovieLoadStateAdapter()
        )

        movieViewModel.hasSearchClick.observe(this, {
            uiHelper.openFragment(this, R.id.parentLayout, SearchFragment())
        })

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            binding!!.swipeLayout.setColorSchemeColors(
                resources.getColor(R.color.app_theme_dark, resources.newTheme())
            )
        } else {
            binding!!.swipeLayout.setColorSchemeColors(
                resources.getColor(R.color.app_theme_dark)
            )
        }

        movieListAdapter.apply {
            listener = { item, position ->
                uiHelper.openActivityAndMovieId(
                    this@MainActivity,
                    MovieDetailActivity::class.java,
                    item.id!!
                )
            }
        }

        binding!!.swipeLayout.setOnRefreshListener {
            movieListAdapter.refresh()
            binding!!.swipeLayout.isRefreshing = false

        }
    }

}