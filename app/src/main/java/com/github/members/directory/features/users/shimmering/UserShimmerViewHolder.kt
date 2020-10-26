package com.github.members.directory.features.users.shimmering

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.github.members.directory.R
import com.github.members.directory.di.providesSharedPrefTheme
import com.github.members.directory.features.ShimmerUser

class UserShimmerViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    private val layoutShimmer: ConstraintLayout = view.findViewById(R.id.layoutShimmer)
    private val isDark = providesSharedPrefTheme(view.context) ?: false
    fun bind(user: ShimmerUser) {
        if(isDark) {
            layoutShimmer.setBackgroundColor(ContextCompat.getColor(view.context, R.color.black))
        } else {
            layoutShimmer.setBackgroundColor(ContextCompat.getColor(view.context, R.color.white))
        }
    }

    companion object {
        fun create(parent: ViewGroup): UserShimmerViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_shimmer, parent, false)
            return UserShimmerViewHolder(view = view)
        }
    }
}