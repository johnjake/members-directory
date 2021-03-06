package com.github.members.directory.features.search.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.members.directory.data.vo.SearchProfile

class SearchAdapter(
        private val itemListener: OnSearchClickListener
) : PagingDataAdapter<SearchProfile, RecyclerView.ViewHolder>(COMPARATOR) {

    // val appDatabase by lazy { GlobalContext.get().koin.get<AppDatabase>() }

    interface OnSearchClickListener {
        fun onItemClickListener(username: String)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if(item != null) {
            (holder as SearchViewHolder).bind(item, position, itemListener)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        SearchViewHolder.create(parent)

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<SearchProfile>() {
            override fun areItemsTheSame(oldItem: SearchProfile, newItem: SearchProfile): Boolean =
                    oldItem.login == newItem.login

            override fun areContentsTheSame(oldItem: SearchProfile, newItem: SearchProfile): Boolean =
                    oldItem == newItem
        }
    }
}

