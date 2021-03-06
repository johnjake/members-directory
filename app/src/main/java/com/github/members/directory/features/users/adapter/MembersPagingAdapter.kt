package com.github.members.directory.features.users.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.github.members.directory.data.vo.Members

class MembersPagingAdapter(
        private val itemListener: DetailsOnClickListener
) : PagingDataAdapter<Members, MembersPagingViewHolder>(repositoryComparator) {

    interface DetailsOnClickListener {
        fun detailsOnClick(username: String)
    }

    companion object {
        private val repositoryComparator = object : DiffUtil.ItemCallback<Members>() {
            override fun areItemsTheSame(oldItem: Members, newItem: Members): Boolean =
                    oldItem.login == newItem.login

            override fun areContentsTheSame(oldItem: Members, newItem: Members): Boolean =
                    oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: MembersPagingViewHolder, position: Int) {
        getItem(position)?.let { user ->
            holder.bind(user, position, itemListener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MembersPagingViewHolder {
       return MembersPagingViewHolder.create(parent)
    }


}

