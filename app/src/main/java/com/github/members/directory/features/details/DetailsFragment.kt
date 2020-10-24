package com.github.members.directory.features.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.members.directory.R
import com.github.members.directory.features.main.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.toolbar_profile_details.*

class DetailsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNavigationViewVisibility()
    }

    override fun onStart() {
        super.onStart()
        toolbar_back.setOnClickListener {
            MainActivity.onBackPress = true
            MainActivity.onVisitedFragment = false
            MainActivity.onDetailsFragment = false
            this.findNavController().popBackStack()
        }
    }

    fun bottomNavigationViewVisibility() {
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView?.visibility = View.GONE
    }

    companion object{
        private const val ARG_CAUGHT = "DetailsFragment"
        fun newInstance(caught: String): DetailsFragment {
            val args: Bundle = Bundle()
            args.putSerializable(ARG_CAUGHT, caught)
            val fragment = DetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }
}