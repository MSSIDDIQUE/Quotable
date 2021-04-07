package com.baymax.quotable.ui.fragments.home_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.baymax.quotable.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nav_controller = findNavController()
        quotes_card.setOnClickListener {
            val directions = HomeFragmentDirections.actionHomeFragmentToQuotesFragment(null,null)
            nav_controller.navigate(directions)
        }
        authors_card.setOnClickListener {
            nav_controller.navigate(R.id.action_home_fragment_to_authorsFragment)
        }
        tags_card.setOnClickListener {
            nav_controller.navigate(R.id.action_home_fragment_to_tagsFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}