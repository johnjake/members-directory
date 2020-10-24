package com.github.members.directory.features.details.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.github.members.directory.R
import com.github.members.directory.data.vo.Members
import com.github.members.directory.di.providesAvatar
import de.hdodenhof.circleimageview.CircleImageView

class FollowingViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    private val imgAvatar: CircleImageView = view.findViewById(R.id.follower_avatar)
    private val userName: TextView = view.findViewById(R.id.txtFollowerName)
    private val baseUrl = providesAvatar()

    fun bind(member: Members) {
        imgAvatar.load(baseUrl + member.id)
        userName.text = member.login
    }

    companion object {
        fun create(parent: ViewGroup): FollowingViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_followers, parent, false)
            return FollowingViewHolder(view)
        }
    }
}