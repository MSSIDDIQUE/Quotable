package com.baymax.quotable.ui.fragments.quotes_fragment.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.baymax.quotable.api.Request
import com.baymax.quotable.utils.exceptions.NoConnectivityException
import com.baymax.quotable.databinding.FragmentQuotesBinding
import com.baymax.quotable.di.Injectable
import com.baymax.quotable.di.injectViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class QuotesFragment : Fragment(), Injectable{

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: QuotesFragmentViewModel
    private lateinit var quotes_adapter: QuotesAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val args : QuotesFragmentArgs by navArgs()

    private var _binding: FragmentQuotesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = FragmentQuotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = injectViewModel(viewModelFactory)
        bindUi()
    }

    override fun onStart() {
        super.onStart()
        if(args.tagName!=null){
            viewModel.updateRequest(Request(null,args.tagName))
        }
        else if(args.authorName!=null){
            viewModel.updateRequest(Request(args.authorName,null))
        }
        else if(args.tagName==null && args.authorName==null){
            viewModel.updateRequest(Request(null,null))
        }
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        quotes_adapter = QuotesAdapter()
        linearLayoutManager = LinearLayoutManager(context)
        _binding?.apply {
            recyclerView.apply {
                setHasFixedSize(true)
                adapter = quotes_adapter.withLoadStateFooter(
                    footer = QuotesLoadStateAdapter{quotes_adapter.retry()}
                )
                layoutManager = linearLayoutManager
            }
        }
    }

    private fun bindUi() = lifecycleScope.launch(Dispatchers.Main) {
        try{
            val quotes = viewModel.quotesPage
            quotes.observe(viewLifecycleOwner){
                quotes_adapter.submitData(viewLifecycleOwner.lifecycle,it)
            }
        }catch (e:NoConnectivityException){
            Toast.makeText(context,"Something went wrong please check your internet connection",Toast.LENGTH_LONG).show()
        }
        _binding?.apply {
            progressBar.visibility = View.GONE
            loadingText.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}