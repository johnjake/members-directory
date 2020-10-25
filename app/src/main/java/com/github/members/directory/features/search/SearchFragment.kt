package com.github.members.directory.features.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.members.directory.R
import com.github.members.directory.features.main.MainActivity
import com.github.members.directory.features.search.adapter.SearchAdapter
import com.github.members.directory.features.search.loader.SearchLoaderStateAdapter
import com.github.members.directory.features.users.UsersFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.layout_search.rvSearch
import kotlinx.android.synthetic.main.toolbar_search.*
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import kotlinx.android.synthetic.main.toolbar_search.search_back as backMain

class SearchFragment : Fragment(), SearchAdapter.OnSearchClickListener {

    private lateinit var resultLayout: LinearLayoutManager
    private val searchAdapter: SearchAdapter by lazy { SearchAdapter(this) }
    private val viewModel: SearchViewModel by inject<SearchViewModel>()
    private var searchJob: Job? = null

    private fun implementSearch(query: String) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchUserProfile(query).collectLatest { data ->
                searchAdapter.submitData(data)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_search, container, false)

    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNavigationViewVisibility()
        initPagingAdapter()
        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY
        implementSearch(query)
        initSearch(query)
        val isDark = getThemeStatePref() ?: false
        if (isDark) {
            searchLayout.setBackgroundColor(ContextCompat.getColor(view.context, R.color.black))
            toolbarSearch.setBackgroundColor(ContextCompat.getColor(view.context, R.color.black))
        } else {
            searchLayout.setBackgroundColor(ContextCompat.getColor(view.context, R.color.white))
            toolbarSearch.setBackgroundColor(ContextCompat.getColor(view.context, R.color.white))
        }
    }

    override fun onStart() {
        super.onStart()
        backMain.setOnClickListener {
            MainActivity.onBackPress = true
            MainActivity.onVisitedFragment = false
            MainActivity.onDetailsFragment = false
            MainActivity.onSearchFragment = false
            this.findNavController().popBackStack()
        }
    }

    private fun getThemeStatePref(): Boolean? {
        val pref = context?.getSharedPreferences(UsersFragment.SHARED_PREF,
                AppCompatActivity.MODE_PRIVATE
        )
        return pref?.getBoolean(UsersFragment.DARK_MODE, false)
    }

    private fun initPagingAdapter() {
        resultLayout = LinearLayoutManager(requireContext()).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        rvSearch.apply {
            layoutManager = resultLayout
            adapter = searchAdapter.withLoadStateHeaderAndFooter(
                    header = SearchLoaderStateAdapter { searchAdapter.retry() },
                    footer = SearchLoaderStateAdapter { searchAdapter.retry() }
            )
            addItemDecoration(decoration)
        }
        searchAdapter.addLoadStateListener { loadState ->
            searchProgress.isVisible = loadState.source.refresh is LoadState.Loading
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

    @InternalCoroutinesApi
    private fun initSearch(query: String) {
        search_input.setText(query)
        search_input.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }
        search_input.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }
        lifecycleScope.launch {
            searchAdapter.loadStateFlow.distinctUntilChangedBy { it.refresh }
                    .filter { it.refresh is LoadState.Loading }
                    .collectLatest { rvSearch.scrollToPosition(0) }
        }
    }

    private fun updateRepoListFromInput() {
        search_input.text.trim().let {
            if (it.isNotEmpty()) {
                implementSearch(it.toString())
            }
        }
    }

    private fun bottomNavigationViewVisibility() {
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView?.visibility = View.GONE
    }

    companion object{
        private const val LAST_SEARCH_QUERY: String = "last_search_query"
        private const val ARG_CAUGHT = "SEARCH FRAGMENT"
        private const val DEFAULT_QUERY = "tawk"
        fun newInstance(caught: String): SearchFragment {
            val args: Bundle = Bundle()
            args.putSerializable(ARG_CAUGHT, caught)
            val fragment = SearchFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onItemClickListener(username: String) {
        MainActivity.onSearchFragment = true
        val argParam = SearchFragmentDirections.actionSearchFragmentToDetailsFragment(username)
        view?.findNavController()?.navigate(argParam)
    }
}