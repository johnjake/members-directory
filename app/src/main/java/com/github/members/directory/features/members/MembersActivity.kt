package com.github.members.directory.features.members

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.members.directory.R
import com.github.members.directory.data.mapper.MembersMapper
import com.github.members.directory.features.members.adapter.MembersPagingAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_members.*
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MembersActivity : AppCompatActivity() {
    private lateinit var resultLayout: LinearLayoutManager
    private val mapper = MembersMapper.getInstance()
    private val memberAdapter: MembersPagingAdapter by lazy { MembersPagingAdapter() }
    private var searchJob: Job? = null

    private val viewModel: MembersViewModel by viewModel()

    @ExperimentalPagingApi
    private fun implementSearchMovies() {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.fetchGithubMembersStream().collectLatest { data ->
                val dbData = data.mapSync { user -> mapper.fromStorage(user) }
                memberAdapter.submitData(dbData)
            }
        }
    }

    @ExperimentalPagingApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initAdapter()
        implementSearchMovies()

        var isDark = getThemeStatePref()
        if (isDark) {
            // dark theme is on
            search_input.setBackgroundResource(R.drawable.search_input_dark_style)
            root_layout.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
            textDetails.setTextColor(ContextCompat.getColor(this, R.color.black))
            textUserName.setTextColor(ContextCompat.getColor(this, R.color.black))
        } else {
            // light theme is on
            search_input.setBackgroundResource(R.drawable.search_input_style)
            root_layout.setBackgroundColor(ContextCompat.getColor(this, R.color.white))

        }

        fab_switcher.setOnClickListener {
            isDark = !isDark
            if (isDark) {
                search_input.setBackgroundResource(R.drawable.search_input_dark_style)
                root_layout.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
                textDetails.setTextColor(ContextCompat.getColor(this, R.color.black))
                textUserName.setTextColor(ContextCompat.getColor(this, R.color.black))
            } else {
                search_input.setBackgroundResource(R.drawable.search_input_style)
                root_layout.setBackgroundColor(ContextCompat.getColor(this, R.color.white))

            }
        }
    }

    private fun initAdapter() {
        resultLayout = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }

        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)

        rvMembers.apply {
            layoutManager = resultLayout
            adapter = memberAdapter
            addItemDecoration(decoration)
        }

        memberAdapter.addLoadStateListener { loadState ->
            resultProgress.isVisible = loadState.source.refresh is LoadState.Loading
            // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    this,
                    "\uD83D\uDE28 Whoops ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        lifecycleScope.launch {
            memberAdapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.Loading }
                .collectLatest {
                    rvMembers.scrollToPosition(0)
                }
        }
    }

    private fun saveThemeStatePref(isDark: Boolean) {
        val pref = applicationContext.getSharedPreferences(SHARED_PREF, MODE_PRIVATE)
        val editor = pref.edit()
        editor.putBoolean(DARK_MODE, isDark)
        editor.apply()
    }

    private fun getThemeStatePref(): Boolean {
        val pref = applicationContext.getSharedPreferences(SHARED_PREF, MODE_PRIVATE)
        return pref.getBoolean(DARK_MODE, false)
    }

    companion object {
        const val DARK_MODE = "isDark"
        const val SHARED_PREF = "myPref"
    }
}