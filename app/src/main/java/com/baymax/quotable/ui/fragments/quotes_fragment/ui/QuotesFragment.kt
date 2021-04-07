package com.baymax.quotable.ui.fragments.quotes_fragment.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.baymax.quotable.R
import com.baymax.quotable.data.api.Request
import com.baymax.quotable.utils.exceptions.NoConnectivityException
import com.baymax.quotable.databinding.FragmentQuotesBinding
import kotlinx.android.synthetic.main.fragment_quotes.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext

class QuotesFragment : Fragment(R.layout.fragment_quotes), KodeinAware{

    override val kodeinContext = kcontext<Fragment>(this)
    override val kodein by kodein()
    private lateinit var viewModel: QuotesFragmentViewModel
    private lateinit var quotes_adapter: QuotesAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val viewModelFactory: QuotesFragmentViewModelFactory by instance()
    private var _binding: FragmentQuotesBinding ? = null
    private val binding get() = _binding!!
    private val args : QuotesFragmentArgs by navArgs()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this,viewModelFactory).get(QuotesFragmentViewModel::class.java)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        quotes_adapter = QuotesAdapter()
        linearLayoutManager = LinearLayoutManager(context)
        _binding = FragmentQuotesBinding.bind(view)
        _binding.apply {
            recycler_view.apply {
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
        progress_bar.visibility = View.GONE
        loading_text.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}