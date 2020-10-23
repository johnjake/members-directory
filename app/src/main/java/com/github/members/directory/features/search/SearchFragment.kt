package com.github.members.directory.features.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.members.directory.R

class SearchFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_search, container, false)

    companion object{
        private const val ARG_CAUGHT = "SEARCH FRAGMENT"
        fun newInstance(caught: String): SearchFragment {
            val args: Bundle = Bundle()
            args.putSerializable(ARG_CAUGHT, caught)
            val fragment = SearchFragment()
            fragment.arguments = args
            return fragment
        }
    }
}