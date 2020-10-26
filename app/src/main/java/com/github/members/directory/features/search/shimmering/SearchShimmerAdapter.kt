package com.github.members.directory.features.search.shimmering

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.members.directory.features.ShimmerUser

class SearchShimmerAdapter : RecyclerView.Adapter<SearchShimmerViewHolder>() {

    private var dataSources: List<ShimmerUser> = emptyList()

    var dataSource: List<ShimmerUser> get() = dataSources
        set(value) {
            dataSources = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchShimmerViewHolder {
       return SearchShimmerViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: SearchShimmerViewHolder, position: Int) {
        holder.bind(dataSources[position])
    }

    override fun getItemCount(): Int = dataSources.size
}