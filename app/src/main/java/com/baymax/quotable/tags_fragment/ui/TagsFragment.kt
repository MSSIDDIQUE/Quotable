package com.baymax.quotable.tags_fragment.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.baymax.quotable.R
import com.baymax.quotable.utils.exceptions.NoConnectivityException
import com.baymax.quotable.tags_fragment.data.Tag
import kotlinx.android.synthetic.main.fragment_tags.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext
import com.baymax.quotable.data.Result
import com.baymax.quotable.databinding.FragmentTagsBinding


class TagsFragment : Fragment(R.layout.fragment_tags), KodeinAware{

    override val kodeinContext = kcontext<Fragment>(this)
    override val kodein by kodein()
    private lateinit var viewModel: TagsFragmentViewModel
    private lateinit var tags_adapter: TagsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val viewModelFactory: TagsFragmentViewModelFactory by instance()
    private var _binding: FragmentTagsBinding ? = null
    private val binding get() = _binding!!

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this,viewModelFactory).get(TagsFragmentViewModel::class.java)
        bindUi()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        linearLayoutManager = LinearLayoutManager(context)
        _binding = FragmentTagsBinding.bind(view)
        _binding.apply {
            recycler_view.apply {
                setHasFixedSize(true)
                layoutManager = linearLayoutManager
            }
        }
    }

    private fun bindUi() = lifecycleScope.launch(Dispatchers.Main) {
        try{
            val quotes = viewModel.quotes
            quotes.observe(viewLifecycleOwner){ result->
                when(result.status){
                    Result.Status.SUCCESS->{
                        tags_adapter = TagsAdapter(result.data as ArrayList<Tag>)
                        linearLayoutManager = LinearLayoutManager(context)
                        recycler_view.layoutManager = linearLayoutManager
                        recycler_view.adapter = tags_adapter
                        progress_bar.visibility = View.GONE
                    }
                    Result.Status.LOADING->{
                        progress_bar.visibility = View.VISIBLE
                    }
                    Result.Status.ERROR->{
                        Toast.makeText(context,"Something went wrong please check your internet connection",Toast.LENGTH_LONG).show()
                    }
                }
                progress_bar.visibility = View.GONE
            }
        }catch (e:NoConnectivityException){
            Toast.makeText(context,"Something went wrong please check your internet connection",Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}