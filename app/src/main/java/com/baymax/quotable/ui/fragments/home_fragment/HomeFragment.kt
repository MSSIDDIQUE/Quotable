package com.baymax.quotable.ui.fragments.home_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.baymax.quotable.R
import com.baymax.quotable.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {


    private var _binding: FragmentHomeBinding ? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nav_controller = findNavController()
        _binding?.quotesCard?.setOnClickListener {
            val directions = HomeFragmentDirections.actionHomeFragmentToQuotesFragment(null,null)
            nav_controller.navigate(directions)
        }
        _binding?.authorsCard?.setOnClickListener {
            nav_controller.navigate(R.id.action_home_fragment_to_authorsFragment)
        }
        _binding?.tagsCard?.setOnClickListener {
            nav_controller.navigate(R.id.action_home_fragment_to_tagsFragment)
        }
    }

}