package com.github.members.directory.features.users.shimmering

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.members.directory.features.ShimmerUser

class UserShimmerAdapter : RecyclerView.Adapter<UserShimmerViewHolder>() {

    private var dataSources: List<ShimmerUser> = emptyList()

    var dataSource: List<ShimmerUser> get() = dataSources
        set(value) {
            dataSources = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserShimmerViewHolder {
       return UserShimmerViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: UserShimmerViewHolder, position: Int) {
        holder.bind(dataSources[position])
    }

    override fun getItemCount(): Int = dataSources.size
}