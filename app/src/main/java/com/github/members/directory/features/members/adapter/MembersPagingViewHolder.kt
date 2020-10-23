package com.github.members.directory.features.members.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.github.members.directory.R
import com.github.members.directory.data.vo.Members
import com.github.members.directory.di.providesAvatar
import de.hdodenhof.circleimageview.CircleImageView


class MembersPagingViewHolder(
    private val view: View
) : RecyclerView.ViewHolder(view) {

    private val imgAvatar: CircleImageView = view.findViewById(R.id.avatar)
    private val txtDetails: TextView = view.findViewById(R.id.textDetails)
    private val txtUsername: TextView = view.findViewById(R.id.textUserName)
    private val url = providesAvatar()

    @SuppressLint("SetTextI18n")
    fun bind(user: Members) {
        txtUsername.text = user.login
        txtDetails.text = user.id.toString()
        imgAvatar.load(url + user.id)
    }

    private fun setImageStrokeDrawable(@DrawableRes id: Int) {
        imgAvatar.background = ResourcesCompat.getDrawable(view.resources, id, null)
    }

    companion object {
        fun create(parent: ViewGroup): MembersPagingViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_members, parent, false)
            return MembersPagingViewHolder(view)
        }
    }
}