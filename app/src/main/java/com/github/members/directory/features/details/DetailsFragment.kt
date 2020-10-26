package com.github.members.directory.features.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.github.members.directory.R
import com.github.members.directory.data.State
import com.github.members.directory.data.vo.Members
import com.github.members.directory.data.vo.Profiles
import com.github.members.directory.di.providesAvatar
import com.github.members.directory.di.providesSaveInternetStatePref
import com.github.members.directory.di.providesSharedOnline
import com.github.members.directory.di.providesSharedPrefTheme
import com.github.members.directory.ext.isOnline
import com.github.members.directory.ext.toast
import com.github.members.directory.features.details.adapter.FollowingAdapter
import com.github.members.directory.features.main.MainActivity
import com.github.members.directory.features.users.UsersFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
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
    private lateinit var resultLayout: LinearLayoutManager
    private val viewModel: DetailsViewModel by viewModel()
    private val followerAdapter: FollowingAdapter by lazy { FollowingAdapter() }
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
        followerObserver()
        initAdapter(view)
        val isLocal = activity?.isOnline(view.context) ?: false
        arguments?.let {
            val arg = DetailsFragmentArgs.fromBundle(it)
            if(isLocal) {
                viewModel.getFollowerList(arg.username)
                providesSaveInternetStatePref(view.context, false)
            }
            viewModel.getProfileDetails(arg.username)
        }

        val isDark = context?.let { providesSharedPrefTheme(it) } ?: false
        if (isDark) {
            detailsLayout.setBackgroundColor(ContextCompat.getColor(view.context, R.color.black))
        } else {
            detailsLayout.setBackgroundColor(ContextCompat.getColor(view.context, R.color.white))
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

    private fun initAdapter(view: View) {
        resultLayout = LinearLayoutManager(view.context).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        rvFollowing.apply {
            layoutManager = resultLayout
            adapter = followerAdapter
        }
    }

    private fun bottomNavigationViewVisibility() {
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView?.visibility = View.GONE
    }

    private fun userProfileObserver() {
        viewModel.stateProfiles.observe(viewLifecycleOwner, Observer { state -> handleDataProfile(state) })
    }

    private fun followerObserver() {
        viewModel.stateList.observe(viewLifecycleOwner, Observer { state -> handleDataFollowers(state) })
    }

    private fun handleDataProfile(state: State<Profiles>?) {
        when(state) {
            is State.Data -> {
                val data = state.data ?: null
                handleProfileSuccess(data)
            }
            is State.Error -> handleProfileFailed(state.error)
            else -> handleProfileNull()
        }
    }

    private fun handleDataFollowers(state: State<List<Members>>) {
        when(state) {
            is State.Data -> handleFollowerSuccess(state.data)
            is State.Error -> handleFollowerFailed(state.error)
            else -> handleProfileNull()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun handleProfileSuccess(profile: Profiles?) {
        profileName.text = profile?.login
        follower.text = profile?.followers.toString()
        following.text = profile?.following.toString()
        biography.text = profile?.bio
        company.text = "Company: ${profile?.company}"
        userName.text = "Name: ${profile?.name}"
        githubUrl.text = "Blog: ${profile?.blog}"
        imgProfile.load(imgUrl + profile?.id)
    }

    private fun handleProfileFailed(error: Throwable) {
        activity?.toast("Error: ${error.localizedMessage}")
    }

    private fun handleProfileNull() {
        activity?.toast("Error: no data found")
    }

    private fun handleFollowerSuccess(list: List<Members>) {
        if(list.isNotEmpty()) {
            followerAdapter.dataSource = list
        }
    }

    private fun handleFollowerFailed(error: Throwable) {
        activity?.toast("Error: ${error.localizedMessage}")
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