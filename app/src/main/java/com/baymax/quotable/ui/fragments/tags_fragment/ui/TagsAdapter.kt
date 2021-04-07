package com.baymax.quotable.ui.fragments.tags_fragment.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.baymax.quotable.databinding.TagsItemBinding
import com.baymax.quotable.ui.fragments.tags_fragment.data.Tag

class TagsAdapter(private val data:ArrayList<Tag>):RecyclerView.Adapter<TagsAdapter.TagsViewHolder> (){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagsViewHolder {
        val binding = TagsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TagsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TagsViewHolder, position: Int) {
        val tag = data[position]
        tag.let {
            holder.bind(createOnClickListener(it.name),it)
        }
    }

    private fun createOnClickListener(tag_name: String): View.OnClickListener {
        return View.OnClickListener {
            val direction = TagsFragmentDirections.actionTagsFragmentToQuotesFragment(tag_name,null)
            it.findNavController().navigate(direction)
        }
    }

    class TagsViewHolder(private val binding: TagsItemBinding): RecyclerView.ViewHolder(binding.root) {
        companion object{
            private val tag_key = "Tags"
        }

        fun bind(listener:View.OnClickListener,data:Tag)
        {
            binding.apply {
                clickListener =listener
                tagData = data
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}