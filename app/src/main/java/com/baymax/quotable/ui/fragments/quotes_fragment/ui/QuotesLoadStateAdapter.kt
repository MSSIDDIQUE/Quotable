package com.baymax.quotable.ui.fragments.quotes_fragment.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.baymax.quotable.databinding.ListViewFooterBinding

class QuotesLoadStateAdapter(
    private val retry:()->Unit
):LoadStateAdapter<QuotesLoadStateAdapter.LoadStateViewHolder>() {


    private var _binding: ListViewFooterBinding? = null
    private val binding get() = _binding!!

    class LoadStateViewHolder(private val view: View) : RecyclerView.ViewHolder(view)

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {

        _binding?.apply {
            loadStateProgress.isVisible = loadState !is LoadState.Loading
            loadStateRetry.isVisible = loadState !is LoadState.Loading
            loadStateErrorMessage.isVisible = loadState !is LoadState.Loading

            if(loadState is LoadState.Error){
                loadStateErrorMessage.text = loadState.error.localizedMessage
            }

            loadStateRetry.setOnClickListener {
                retry.invoke()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        _binding = ListViewFooterBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LoadStateViewHolder(
            binding.root
        )
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        _binding = null
    }
}