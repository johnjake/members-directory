package com.github.members.directory.features.search.offline

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.members.directory.data.vo.Members

class OfflineAdapter : RecyclerView.Adapter<OfflineViewHolder>() {
    private var dataSources: List<Members> = emptyList()

    var dataSource: List<Members> get() = dataSources
        set(value) {
            dataSources = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfflineViewHolder {
        return  OfflineViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: OfflineViewHolder, position: Int) {
        holder.bind(dataSources[position])
    }

    override fun getItemCount(): Int = dataSources.size
}