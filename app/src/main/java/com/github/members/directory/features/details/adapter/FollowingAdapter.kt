package com.github.members.directory.features.details.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.members.directory.data.vo.Members

class FollowingAdapter : RecyclerView.Adapter<FollowingViewHolder>() {

    private var dataSources: List<Members> = emptyList()

    var dataSource: List<Members> get() = dataSources
    set(value) {
        dataSources = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingViewHolder {
       return FollowingViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        holder.bind(dataSources[position])
    }

    override fun getItemCount(): Int = dataSources.size

}