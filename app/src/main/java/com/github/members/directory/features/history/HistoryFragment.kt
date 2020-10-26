package com.github.members.directory.features.history

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.github.members.directory.R
import com.github.members.directory.data.State
import com.github.members.directory.data.vo.SearchProfile
import com.github.members.directory.di.providesSharedOnline
import com.github.members.directory.di.providesSharedPrefTheme
import com.github.members.directory.ext.toast
import com.github.members.directory.features.history.adapter.HistoryAdapter
import com.github.members.directory.features.history.adapter.SliderPagerAdapter
import kotlinx.android.synthetic.main.fragment_history.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class HistoryFragment : Fragment() {
    private lateinit var resultLayout: LinearLayoutManager
    private val viewModel: HistoryViewModel by viewModel()
    private val sliderAdapter by lazy { context?.let { SliderPagerAdapter(it) } }
    private val searchAdapter: HistoryAdapter by lazy { HistoryAdapter() }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_history, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleObserver()
        handleSearchObserver()
        val isLocal: Boolean = providesSharedOnline(view.context) ?: false
        if(!isLocal) {
            viewModel.getGithubTopUsers()
        }
        viewModel.getUserSearchList()
        val isDark = context?.let { providesSharedPrefTheme(it) } ?: false
        if (isDark) {
            historyLayout.setBackgroundColor(ContextCompat.getColor(view.context, R.color.black))
            indicator.setBackgroundColor(ContextCompat.getColor(view.context, R.color.black))
        } else {
            historyLayout.setBackgroundColor(ContextCompat.getColor(view.context, R.color.white))
            indicator.setBackgroundColor(ContextCompat.getColor(view.context, R.color.white))
        }
        initAdapter(view)
    }

    private fun initSlider(list: List<SearchProfile>) {
        sliderAdapter?.dataSource = list
        slider_pager.adapter = sliderAdapter
        val timer = Timer()
        timer.scheduleAtFixedRate(activity?.let { SliderTimer(it, slider_pager, list) }, 4000, 6000)
        indicator.setupWithViewPager(slider_pager, true)
    }

    private fun initAdapter(view: View) {
        resultLayout = LinearLayoutManager(view.context).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        listView.apply {
            layoutManager = resultLayout
            adapter = searchAdapter
        }
    }

    private fun handleObserver() {
        viewModel.stateProfile.observe(viewLifecycleOwner, Observer { state -> handleData(state) })
    }

    private fun handleSearchObserver() {
        viewModel.stateSearch.observe(viewLifecycleOwner, Observer { state -> handleDataSearch(state) })
    }

    private fun handleDataSearch(state: State<List<SearchProfile>>) {
        when(state) {
            is State.Data -> handleSearchSuccess(state.data)
            is State.Error -> handleSearchFailure(state.error)
            else -> handleProfileNull()
        }
    }

    private fun handleData(state: State<List<SearchProfile>>) {
        when(state) {
            is State.Data -> handleSuccess(state.data)
            is State.Error -> handleFailure(state.error)
            else -> handleProfileNull()
        }
    }

    private fun handleSearchSuccess(list: List<SearchProfile>) {
        if(list.isNotEmpty()) {
            searchAdapter.dataSource = list
        }
    }

    private fun handleSearchFailure(error: Throwable) {
        activity?.toast("Error: ${error.localizedMessage}")
    }

    private fun handleSuccess(list: List<SearchProfile>) {
        if(list.isNotEmpty()) {
            initSlider(list.take(8))
        }
    }

    private fun handleFailure(error: Throwable) {
        activity?.toast("Error: ${error.localizedMessage}")
    }

    private fun handleProfileNull() {
        activity?.toast("Error: no data found")
    }

    companion object{
        private const val ARG_CAUGHT = "FragmentRepository"
        fun newInstance(caught: String): HistoryFragment {
            val args: Bundle = Bundle()
            args.putSerializable(ARG_CAUGHT, caught)
            val fragment = HistoryFragment()
            fragment.arguments = args
            return fragment
        }
    }

    internal class SliderTimer(private val activity: Activity,
                               private val sliderPager: ViewPager,
                               private val lstSlider: List<SearchProfile>) : TimerTask() {
        override fun run() {
            activity.runOnUiThread(Runnable {
                if (sliderPager.currentItem < lstSlider.size - 1) {
                    sliderPager.currentItem = sliderPager.currentItem + 1
                } else sliderPager.currentItem = 0
            })
        }
    }
}