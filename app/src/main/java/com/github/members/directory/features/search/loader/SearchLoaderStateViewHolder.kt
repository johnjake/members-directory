package com.github.members.directory.features.search.loader

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.github.members.directory.R

class SearchLoaderStateViewHolder(
        view: View,
        private val retry: ()-> Unit
) : RecyclerView.ViewHolder(view) {
    var motionLayout: MotionLayout = view.findViewById(R.id.mainLoader)

    init {
        view.findViewById<Button>(R.id.btnRetry).setOnClickListener {
            retry()
        }
    }

    fun bind(loadState: LoadState) {
        when (loadState) {
            is LoadState.Loading -> motionLayout.transitionToEnd()
            else -> motionLayout.transitionToStart()
        }
    }

    companion object {
        fun getInstance(parent: ViewGroup, retry: () -> Unit): SearchLoaderStateViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.item_search_loader, parent, false)
            return SearchLoaderStateViewHolder(view, retry)
        }
    }
}