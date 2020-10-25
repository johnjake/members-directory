package com.github.members.directory.features.repository

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.members.directory.R

class RepositoryFragment : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_repository, container, false)

    companion object{
        private const val ARG_CAUGHT = "FragmentRepository"
        fun newInstance(caught: String): RepositoryFragment {
            val args: Bundle = Bundle()
            args.putSerializable(ARG_CAUGHT, caught)
            val fragment = RepositoryFragment()
            fragment.arguments = args
            return fragment
        }
    }
}