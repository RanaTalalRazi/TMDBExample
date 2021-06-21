package com.ranatalal.tmdbexample.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ranatalal.tmdbexample.R
import com.ranatalal.tmdbexample.databinding.NetworkStateItemBinding
import com.ranatalal.tmdbexample.views.models.MovieListResponseModel

class MovieLoadStateAdapter(var listener: () -> Unit) :
    LoadStateAdapter<RecyclerView.ViewHolder>() {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, loadState: LoadState) {
        val viewHolder: ViewHolder = holder as ViewHolder
        viewHolder.binding.progressBar.isVisible = loadState is LoadState.Loading
        viewHolder.binding.retryButton.isVisible = loadState is LoadState.Error
        viewHolder.binding.errorMsg.isVisible =
            !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
        viewHolder.binding.errorMsg.text = (loadState as? LoadState.Error)?.error?.message
        viewHolder.binding.retryButton.setOnClickListener {
            listener()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): RecyclerView.ViewHolder {
        val binding: NetworkStateItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.network_state_item, parent, false
        )
        return ViewHolder(binding)
    }

    class ViewHolder(var binding: NetworkStateItemBinding) :
        RecyclerView.ViewHolder(binding.root)

}