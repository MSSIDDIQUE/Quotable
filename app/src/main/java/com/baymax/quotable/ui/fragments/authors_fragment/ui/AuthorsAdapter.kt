package com.baymax.quotable.ui.fragments.authors_fragment.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.baymax.quotable.ui.fragments.authors_fragment.data.Author
import com.baymax.quotable.databinding.AuthorItemBinding

class AuthorsAdapter(): PagingDataAdapter<Author, AuthorsAdapter.AuthorsViewHolder>(AuthorsAdapter.AuthorsComparator){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuthorsViewHolder {
        val inflatedView = AuthorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AuthorsViewHolder(inflatedView)
    }


    override fun onBindViewHolder(holder: AuthorsAdapter.AuthorsViewHolder, position: Int) {
        val author = getItem(position)
        author.let {
            holder.bind(createOnClickListener(it!!.name),it)
        }
    }

    private fun createOnClickListener(author_name: String): View.OnClickListener {
        return View.OnClickListener {
            val direction = AuthorsFragmentDirections.actionAuthorsFragmentToQuotesFragment(null, author_name)
            it.findNavController().navigate(direction)
        }
    }

    class AuthorsViewHolder(private val binding: AuthorItemBinding): RecyclerView.ViewHolder(binding.root) {
        companion object{
            private val Authors_Key = "Authors"
        }

        fun bind(listener:View.OnClickListener,data:Author)
        {
            binding.apply {
                clickListener = listener
                authorData = data
            }
        }
    }

    companion object{
        private val AuthorsComparator = object : DiffUtil.ItemCallback<Author>(){
            override fun areItemsTheSame(oldItem: Author, newItem: Author): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Author, newItem: Author): Boolean {
                return oldItem == newItem
            }
        }
    }
}