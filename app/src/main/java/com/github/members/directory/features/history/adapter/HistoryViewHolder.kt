package com.github.members.directory.features.history.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.github.members.directory.R
import com.github.members.directory.data.vo.SearchProfile
import de.hdodenhof.circleimageview.CircleImageView

class HistoryViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private val avatar: CircleImageView = view.findViewById(R.id.search_avatar)
    private val txtName: TextView = view.findViewById(R.id.txtSearchName)

    fun bind(user: SearchProfile) {
        if(user!=null) {
            avatar.load(user.avatar_url)
            txtName.text = user.login
        }
    }

    companion object {
        fun create(parent: ViewGroup): HistoryViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_discover, parent, false)
            return HistoryViewHolder(view)
        }
    }
}