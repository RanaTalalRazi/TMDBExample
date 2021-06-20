package com.ranatalal.tmdbexample.views.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.ranatalal.tmdbexample.R
import com.ranatalal.tmdbexample.databinding.FragmentSearchBinding
import com.ranatalal.tmdbexample.helper.UIHelper
import com.ranatalal.tmdbexample.viewmodel.MovieViewModel
import com.ranatalal.tmdbexample.views.adapter.MovieListAdapter
import com.ranatalal.tmdbexample.views.adapters.MovieLoadStateAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.stateViewModel


class SearchFragment : BaseFragment(), TextWatcher {
    private val movieListAdapter by lazy {
        MovieListAdapter(requireContext())
    }
    val uiHelper: UIHelper by inject()
    val movieViewModel: MovieViewModel by stateViewModel()


    var binding: FragmentSearchBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            FragmentSearchBinding.inflate(inflater, container, false)

        binding!!.viewModel = movieViewModel

//back press

        uiHelper.hideSoftKeyboard(requireContext(), binding!!.searchBar)


        movieViewModel.hasSearchTextClearClick.observe(viewLifecycleOwner) {
            binding!!.searchBar.setText("")
        }

        binding!!.searchBar.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                updatedSubredditFromInput()
//                uiHelper.hideSoftKeyboard(requireContext(), binding!!.searchBar)

                return@OnEditorActionListener true
            }
            false
        })

        binding!!.searchBar.addTextChangedListener(this)

        movieViewModel.getSearchedProducts()

        lifecycleScope.launchWhenCreated {
            movieListAdapter.loadStateFlow.collectLatest { loadStates ->
                if (loadStates.refresh is LoadState.Error) {
                    if (!binding!!.searchBar.text.toString().equals(""))
                        uiHelper.showLongToastInCenter(
                            requireContext(),
                            (loadStates.refresh as LoadState.Error).error.message ?: ""
                        )
                }
                binding!!.swipeLayout.isRefreshing = loadStates.refresh is LoadState.Loading
            }
        }

        lifecycleScope.launch {
            movieViewModel.searchProductListDataResponse?.collectLatest {
                movieListAdapter.submitData(it)
            }
        }

        binding!!.recyclerView.adapter = movieListAdapter.withLoadStateHeaderAndFooter(
            header = MovieLoadStateAdapter(),
            footer = MovieLoadStateAdapter()
        )







        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            binding!!.swipeLayout.setColorSchemeColors(
                resources.getColor(R.color.app_theme_dark, resources.newTheme())
            )
        } else {
            binding!!.swipeLayout.setColorSchemeColors(
                resources.getColor(R.color.app_theme_dark)
            )
        }

        binding!!.swipeLayout.setOnRefreshListener {
            movieListAdapter.refresh()
            binding!!.swipeLayout.isRefreshing = false

        }

        return binding!!.root
    }


    private fun updatedSubredditFromInput() {
        binding!!.searchBar.text.trim().toString().let {
            if (/*it.isNotBlank() &&*/ movieViewModel.shouldShowSubreddit(it)) {
                movieViewModel.showSubreddit(it)
                uiHelper.hideSoftKeyboard(requireContext(), binding!!.searchBar)
            }
        }
    }


    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (count==0){
            binding!!.clearButton.visibility=View.GONE

        }else{
            binding!!.clearButton.visibility=View.VISIBLE

        }
    }

    override fun afterTextChanged(s: Editable?) {
       if (s!!.length==0){
           updatedSubredditFromInput()
       }
    }

}