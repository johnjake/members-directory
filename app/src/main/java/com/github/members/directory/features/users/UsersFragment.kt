package com.github.members.directory.features.users

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.members.directory.R
import com.github.members.directory.data.mapper.MembersMapper
import com.github.members.directory.di.providesSharedPrefTheme
import com.github.members.directory.ext.isOnline
import com.github.members.directory.features.Shimmer
import com.github.members.directory.features.main.MainActivity
import com.github.members.directory.features.users.adapter.MembersPagingAdapter
import com.github.members.directory.features.users.shimmering.UserShimmerAdapter
import com.github.members.directory.utils.alertDialog.ListenerCallBack
import com.github.members.directory.utils.alertDialog.VelloAlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_members.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsersFragment : Fragment(), MembersPagingAdapter.DetailsOnClickListener {
    private lateinit var resultLayout: LinearLayoutManager
    private val mapper = MembersMapper.getInstance()
    private val memberAdapter: MembersPagingAdapter by lazy { MembersPagingAdapter(this) }
    private val shimmerAdapter: UserShimmerAdapter by lazy { UserShimmerAdapter() }
    private var searchJob: Job? = null
    private val viewModel: MembersViewModel by viewModel()

    @ExperimentalPagingApi
    private fun implementSearchMovies() {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.fetchGithubMembersStream().distinctUntilChanged().collectLatest { data ->
                val dbData = data.mapSync { user -> mapper.fromStorage(user) }
                memberAdapter.submitData(dbData)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_members, container, false)

    @ExperimentalPagingApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter(view)
        initShimmerAdapter(view)
        val isLocal: Boolean = activity?.isOnline(view.context) ?: false
        var isDark = context?.let { providesSharedPrefTheme(it) } ?: false
        if (isDark) {
            // dark theme is on
            search_input.setBackgroundResource(R.drawable.search_input_dark_style)
            root_layout.setBackgroundColor(ContextCompat.getColor(view.context, R.color.black))

        } else {
            // light theme is on
            search_input.setBackgroundResource(R.drawable.search_input_style)
            root_layout.setBackgroundColor(ContextCompat.getColor(view.context, R.color.white))

        }

        fab_switcher.setOnClickListener {
            isDark = !isDark
            if (isDark) {
                search_input.setBackgroundResource(R.drawable.search_input_dark_style)
                root_layout.setBackgroundColor(ContextCompat.getColor(view.context, R.color.black))
                saveThemeStatePref(isDark)

            } else {
                search_input.setBackgroundResource(R.drawable.search_input_style)
                root_layout.setBackgroundColor(ContextCompat.getColor(view.context, R.color.white))
                saveThemeStatePref(isDark)
            }
        }
        implementSearchMovies()
        search_input.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus) {
                if(isLocal) {
                    v.findNavController().navigate(R.id.action_main_to_search)
                }else {
                    offLine(v)
                }
            }
        }

        userButton.setOnClickListener {
            memberAdapter.retry()
        }
    }

    override fun onResume() {
        super.onResume()
        bottomVisibility()
        shimmeringEffects()
    }

    private fun offLine(view: View) {
        val alertDialog = VelloAlertDialog()
        alertDialog.alertInitialize(
                view.context,
                "Ops! Offline",
                "You are currently offline would you like to continue?",
                Typeface.SANS_SERIF,
                Typeface.DEFAULT_BOLD,
                isCancelable = true,
                isNegativeBtnHide = true)
        alertDialog.setPositive("YES", object : ListenerCallBack {
            override fun onClick(dialog: VelloAlertDialog) {
                view.findNavController().navigate(R.id.action_main_to_search)
                dialog.dismiss()
            }
        })
        alertDialog.show()
    }

    private fun initShimmerAdapter(view: View) {
        val data = Shimmer.getInstance()
        val shimLayout = LinearLayoutManager(view.context).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        rvShimUser.apply {
            layoutManager = shimLayout
            adapter = shimmerAdapter
        }
        shimmerAdapter.dataSource = data.getShimmerList()
    }

    private fun initAdapter(view: View) {
        resultLayout = LinearLayoutManager(view.context).apply {
            orientation = LinearLayoutManager.VERTICAL
        }

        val decoration = DividerItemDecoration(view.context, DividerItemDecoration.VERTICAL)

        rvMembers.apply {
            layoutManager = resultLayout
            adapter = memberAdapter
            addItemDecoration(decoration)
        }

        memberAdapter.addLoadStateListener { loadState ->
            val resultLoading: Boolean = loadState.source.refresh is LoadState.Loading
            if (resultProgress != null) resultProgress.isVisible = resultLoading ?: false
            if (userButton != null) {
                userButton.isVisible = loadState.source.refresh is LoadState.Error
            }
            // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    context,
                    "\uD83D\uDE28 Whoops ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    @Suppress("DeferredResultUnused")
    private fun shimmeringEffects() {
        if (containerUser != null) {
            resultProgress.isVisible = false
            containerUser.isVisible = true
            rvMembers.isVisible = false
            containerUser.startLayoutAnimation()
            val shimmerEffect = CoroutineScope(Dispatchers.Main)
            shimmerEffect.async {
                delay(1500)
                containerUser.stopShimmer()
                rvMembers.isVisible = true
                containerUser.isVisible = false
            }
        }
    }

    private fun saveThemeStatePref(isDark: Boolean) {
        val pref = context?.getSharedPreferences(SHARED_PREF,
            AppCompatActivity.MODE_PRIVATE
        )
        val editor = pref?.edit()
        editor?.putBoolean(DARK_MODE, isDark)
        editor?.apply()
    }

    private fun bottomVisibility() {
        if(MainActivity.onBackPress) {
            MainActivity.onBackPress = false
            val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)
            bottomNavigationView?.visibility = View.VISIBLE
        }
    }

    companion object{
        const val DARK_MODE = "isDark"
        const val SHARED_PREF = "myPref"

        private const val ARG_CAUGHT = "FragmentMembers"
        fun newInstance(caught: String): UsersFragment {
            val args: Bundle = Bundle()
            args.putSerializable(ARG_CAUGHT, caught)
            val fragment = UsersFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun detailsOnClick(username: String) {
        MainActivity.onDetailsFragment = true
        MainActivity.onVisitedFragment = false
        MainActivity.onSearchFragment = false
        val actionByParam = UsersFragmentDirections.actionMainToDetails(username)
        findNavController().navigate(actionByParam)
    }
}