package com.github.members.directory.features.history.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.members.directory.data.vo.SearchProfile

class HistoryAdapter : RecyclerView.Adapter<HistoryViewHolder>() {

    private var dataSources: List<SearchProfile> = emptyList()

    var dataSource: List<SearchProfile> get() = dataSources
        set(value) {
            dataSources = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(dataSources[position])
    }

    override fun getItemCount(): Int = dataSources.size
}