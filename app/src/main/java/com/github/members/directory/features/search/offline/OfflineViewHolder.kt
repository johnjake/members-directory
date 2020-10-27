package com.github.members.directory.features.search.offline

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.github.members.directory.R
import com.github.members.directory.data.vo.Members
import de.hdodenhof.circleimageview.CircleImageView

class OfflineViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    private val avatar: CircleImageView = view.findViewById(R.id.searchAvatar)
    private val textUserName: TextView = view.findViewById(R.id.textUserName)
    private val textDetails: TextView = view.findViewById(R.id.textDetails)

    fun bind(profile: Members) {
        avatar.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_default_avatar))
        textUserName.text = profile.login
        textDetails.text = profile.type
    }

    companion object {
        fun create(parent: ViewGroup): OfflineViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_search, parent, false)
            return OfflineViewHolder(view)
        }
    }

}