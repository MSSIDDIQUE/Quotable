package com.baymax.quotable.ui.fragments.authors_fragment.ui

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
import com.baymax.quotable.databinding.FragmentAthorsBinding
import com.baymax.quotable.di.Injectable
import com.baymax.quotable.di.injectViewModel
import com.baymax.quotable.ui.fragments.quotes_fragment.ui.QuotesLoadStateAdapter
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthorsFragment : Fragment(), Injectable{

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: AuthorsFragmentViewModel
    private lateinit var authors_adapter: AuthorsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var _binding: FragmentAthorsBinding ? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        viewModel = injectViewModel(viewModelFactory)
        bindUi()
        _binding = FragmentAthorsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authors_adapter = AuthorsAdapter()
        linearLayoutManager = LinearLayoutManager(context)
        _binding?.apply {
            recyclerView.apply {
                setHasFixedSize(true)
                adapter = authors_adapter.withLoadStateFooter(
                    footer = QuotesLoadStateAdapter{authors_adapter.retry()}
                )
                layoutManager = linearLayoutManager
            }
        }
    }

    private fun bindUi() = lifecycleScope.launch(Dispatchers.Main) {
        try{
            val authors = viewModel.authorsPage
            authors.observe(viewLifecycleOwner){
                authors_adapter.submitData(viewLifecycleOwner.lifecycle,it)
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