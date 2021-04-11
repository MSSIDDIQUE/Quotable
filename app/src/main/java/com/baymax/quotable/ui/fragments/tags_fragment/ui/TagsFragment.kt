package com.baymax.quotable.ui.fragments.tags_fragment.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.baymax.quotable.utils.exceptions.NoConnectivityException
import com.baymax.quotable.ui.fragments.tags_fragment.data.Tag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.baymax.quotable.data.Result
import com.baymax.quotable.databinding.FragmentTagsBinding
import com.baymax.quotable.di.Injectable
import com.baymax.quotable.di.injectViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class TagsFragment : Fragment(),Injectable{

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: TagsFragmentViewModel
    private lateinit var tags_adapter: TagsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var _binding: FragmentTagsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = FragmentTagsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = injectViewModel(viewModelFactory)
        bindUi()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        linearLayoutManager = LinearLayoutManager(context)
        _binding?.apply {
            recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = linearLayoutManager
            }
        }
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    private fun bindUi() = lifecycleScope.launch(Dispatchers.Main) {
        try{
            val quotes = viewModel.quotes
            quotes.observe(viewLifecycleOwner){ result->
                when(result.status){
                    Result.Status.SUCCESS->{
                        tags_adapter = TagsAdapter(result.data as ArrayList<Tag>)
                        linearLayoutManager = LinearLayoutManager(context)
                        _binding?.apply {
                            recyclerView.layoutManager = linearLayoutManager
                            recyclerView.adapter = tags_adapter
                            progressBar.visibility = View.GONE
                            loadingText.visibility = View.GONE
                        }
                    }
                    Result.Status.LOADING->{
                        _binding?.apply {
                            progressBar.visibility = View.VISIBLE
                            loadingText.visibility = View.VISIBLE
                        }
                    }
                    Result.Status.ERROR->{
                        Toast.makeText(context,"Something went wrong please check your internet connection",Toast.LENGTH_LONG).show()
                    }
                }
                _binding?.apply {
                    progressBar.visibility = View.GONE
                    loadingText.visibility = View.GONE
                }
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