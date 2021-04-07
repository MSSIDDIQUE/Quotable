package com.baymax.quotable.ui.fragments.quotes_fragment.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.baymax.quotable.R
import kotlinx.android.synthetic.main.list_view_footer.view.*

class QuotesLoadStateAdapter(
    private val retry:()->Unit
):LoadStateAdapter<QuotesLoadStateAdapter.LoadStateViewHolder>() {

    class LoadStateViewHolder(private val view: View) : RecyclerView.ViewHolder(view)

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        val progress_bar = holder.itemView.load_state_progress
        val btn_retry = holder.itemView.load_state_retry
        val error_message = holder.itemView.load_state_errorMessage

        btn_retry.isVisible = loadState !is LoadState.Loading
        error_message.isVisible = loadState !is LoadState.Loading
        progress_bar.isVisible = loadState is LoadState.Loading

        if(loadState is LoadState.Error){
            error_message.text = loadState.error.localizedMessage
        }

        btn_retry.setOnClickListener {
            retry.invoke()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_view_footer,parent,false)
        )
    }
}