package com.baymax.quotable.ui.fragments.authors_fragment.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.baymax.quotable.R
import com.baymax.quotable.utils.exceptions.NoConnectivityException
import com.baymax.quotable.databinding.FragmentAthorsBinding
import com.baymax.quotable.di.Injectable
import com.baymax.quotable.di.injectViewModel
import com.baymax.quotable.ui.fragments.quotes_fragment.ui.QuotesLoadStateAdapter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_athors.progress_bar
import kotlinx.android.synthetic.main.fragment_quotes.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext
import javax.inject.Inject

class AuthorsFragment : Fragment(R.layout.fragment_athors), Injectable{

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: AuthorsFragmentViewModel
    private lateinit var authors_adapter: AuthorsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var _binding: FragmentAthorsBinding ? = null
    private val binding get() = _binding!!

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = injectViewModel(viewModelFactory)
        bindUi()
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authors_adapter = AuthorsAdapter()
        linearLayoutManager = LinearLayoutManager(context)
        _binding = FragmentAthorsBinding.bind(view)
        _binding.apply {
            recycler_view.apply {
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
                progress_bar.visibility = View.GONE
                loading_text.visibility = View.GONE
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