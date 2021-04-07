package com.baymax.quotable.ui.fragments.quotes_fragment.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.baymax.quotable.databinding.QuoteItemBinding
import com.baymax.quotable.ui.fragments.quotes_fragment.data.Quote

class QuotesAdapter():
    PagingDataAdapter<Quote, QuotesAdapter.QuotesViewHolder>(QuotesCompator){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuotesViewHolder {
        val binding = QuoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuotesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuotesViewHolder, position: Int) {
        val quote = getItem(position)
        quote.let {
            holder.bind(it!!)
        }
    }

    class QuotesViewHolder(private val binding: QuoteItemBinding): RecyclerView.ViewHolder(binding.root) {
        companion object{
            private val quotes_key = "Quotes"
        }

        fun bind(data:Quote)
        {
            binding.apply {
                quoteData = data
            }
        }
    }


    companion object{
        private val QuotesCompator = object : DiffUtil.ItemCallback<Quote>(){
            override fun areItemsTheSame(oldItem: Quote, newItem: Quote): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Quote, newItem: Quote): Boolean {
                return oldItem == newItem
            }
        }
    }
}