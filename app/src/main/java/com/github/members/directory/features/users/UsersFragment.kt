package com.github.members.directory.features.users

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
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.members.directory.R
import com.github.members.directory.data.mapper.MembersMapper
import com.github.members.directory.ext.toast
import com.github.members.directory.features.users.adapter.MembersPagingAdapter
import kotlinx.android.synthetic.main.fragment_members.*
import kotlinx.android.synthetic.main.item_members.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsersFragment : Fragment(), MembersPagingAdapter.DetailsOnClickListener {
    private lateinit var resultLayout: LinearLayoutManager
    private val mapper = MembersMapper.getInstance()
    private val memberAdapter: MembersPagingAdapter by lazy { MembersPagingAdapter(this) }
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
        var isDark = getThemeStatePref() ?: false
        if (isDark) {
            // dark theme is on
            search_input.setBackgroundResource(R.drawable.search_input_dark_style)
            root_layout.setBackgroundColor(ContextCompat.getColor(view.context, R.color.black))
            textDetails.setTextColor(ContextCompat.getColor(view.context, R.color.black))
            textUserName.setTextColor(ContextCompat.getColor(view.context, R.color.black))
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

            } else {
                search_input.setBackgroundResource(R.drawable.search_input_style)
                root_layout.setBackgroundColor(ContextCompat.getColor(view.context, R.color.white))

            }
        }
        implementSearchMovies()
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
            val resultLoading: Boolean? = loadState.source.refresh is LoadState.Loading
            if (resultProgress != null) resultProgress.isVisible = resultLoading ?: false
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

    private fun saveThemeStatePref(isDark: Boolean) {
        val pref = context?.getSharedPreferences(SHARED_PREF,
            AppCompatActivity.MODE_PRIVATE
        )
        val editor = pref?.edit()
        editor?.putBoolean(DARK_MODE, isDark)
        editor?.apply()
    }

    private fun getThemeStatePref(): Boolean? {
        val pref = context?.getSharedPreferences(SHARED_PREF,
            AppCompatActivity.MODE_PRIVATE
        )
        return pref?.getBoolean(DARK_MODE, false)
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
        activity?.toast("this is $username")
    }
}