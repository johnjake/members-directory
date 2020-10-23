package com.github.members.directory.features.visited

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.members.directory.R

class VisitedFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_visited, container, false)

    companion object{
        private const val ARG_CAUGHT = "FragmentVisited"
        fun newInstance(caught: String): VisitedFragment {
            val args: Bundle = Bundle()
            args.putSerializable(ARG_CAUGHT, caught)
            val fragment = VisitedFragment()
            fragment.arguments = args
            return fragment
        }
    }
}