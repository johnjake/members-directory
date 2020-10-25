package com.github.members.directory.features.search.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.github.members.directory.R
import com.github.members.directory.data.vo.SearchProfile
import de.hdodenhof.circleimageview.CircleImageView

class SearchViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private val avatar: CircleImageView = view.findViewById(R.id.searchAvatar)
    private val userName: TextView = view.findViewById(R.id.textUserName)
    private val userType: TextView = view.findViewById(R.id.textDetails)

    @SuppressLint("SetTextI18n")
    fun bind(user: SearchProfile) {
        avatar.load(user.avatar_url)
        userName.text = user.login
        userType.text = user.type
    }

    companion object {
        fun create(parent: ViewGroup): SearchViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_search, parent, false)
            return SearchViewHolder(view = view)
        }
    }
}