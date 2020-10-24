package com.github.members.directory.features.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import coil.load
import com.github.members.directory.R
import com.github.members.directory.data.State
import com.github.members.directory.data.vo.Profiles
import com.github.members.directory.di.providesAvatar
import com.github.members.directory.ext.toast
import com.github.members.directory.features.main.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_details.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlinx.android.synthetic.main.toolbar_profile_details.toolbar_back as back
import kotlinx.android.synthetic.main.toolbar_profile_details.userProfileName as profileName
import kotlinx.android.synthetic.main.fragment_details.txtFollowerNumber as follower
import kotlinx.android.synthetic.main.fragment_details.txtFollowingNumber as following
import kotlinx.android.synthetic.main.fragment_details.imgDetail as imgProfile
import kotlinx.android.synthetic.main.fragment_details.txtBiography as biography
import kotlinx.android.synthetic.main.fragment_details.txtName as userName
import kotlinx.android.synthetic.main.fragment_details.txtCompany as company
import kotlinx.android.synthetic.main.fragment_details.txtBlog as githubUrl

class DetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by viewModel()
    private val imgUrl = providesAvatar()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNavigationViewVisibility()
        userProfileObserver()

        arguments?.let {
            val arg = DetailsFragmentArgs.fromBundle(it)
            viewModel.getProfileDetails(arg.username)
        }
    }

    override fun onStart() {
        super.onStart()
        back.setOnClickListener {
            MainActivity.onBackPress = true
            MainActivity.onVisitedFragment = false
            MainActivity.onDetailsFragment = false
            this.findNavController().popBackStack()
        }
    }

    override fun onResume() {
        super.onResume()
    }

    private fun bottomNavigationViewVisibility() {
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView?.visibility = View.GONE
    }

    private fun userProfileObserver() {
        viewModel.stateProfiles.observe(viewLifecycleOwner, Observer { state -> handleDataProfile(state) })
    }

    private fun handleDataProfile(state: State<Profiles>) {
        when(state) {
            is State.Data -> handleProfileSuccess(state.data)
            is State.Error -> handleProfileFailed(state.error)
            else -> handleProfileNull()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun handleProfileSuccess(profile: Profiles) {
        profileName.text = profile.login
        follower.text = profile.followers.toString()
        following.text = profile.following.toString()
        biography.text = profile.bio
        company.text = "Company: ${profile.company}"
        userName.text = "Name: ${profile.name}"
        githubUrl.text = "Blog: ${profile.blog}"
        imgProfile.load(imgUrl + profile.id)
    }

    private fun handleProfileFailed(error: Throwable) {
        activity?.toast("Error: ${error.localizedMessage}")
    }

    private fun handleProfileNull() {
        activity?.toast("Error: no data found")
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