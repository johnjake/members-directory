package com.github.members.directory.features.members.adapter

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
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
import com.github.members.directory.utils.ImageUtils
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import timber.log.Timber


class MembersPagingViewHolder(
    private val view: View
) : RecyclerView.ViewHolder(view) {

    private val imgAvatar: CircleImageView = view.findViewById(R.id.avatar)
    private val txtDetails: TextView = view.findViewById(R.id.textDetails)
    private val txtUsername: TextView = view.findViewById(R.id.textUserName)
    private val url = providesAvatar()
    private val scope = CoroutineScope(Dispatchers.IO)

    @SuppressLint("SetTextI18n")
    fun bind(user: Members, position: Int) {
        txtUsername.text = user.login
        txtDetails.text = user.type
        imgAvatar.load(url + user.id)
        if(position%4 ==0) {
            setImageStrokeDrawable(R.drawable.stroke_avatar_gold)
        } else {
            setImageStrokeDrawable(R.drawable.stroke_avatar_gray)
        }
    }

    private fun setImageStrokeDrawable(@DrawableRes id: Int) {
        imgAvatar.background = ResourcesCompat.getDrawable(view.resources, id, null)
    }

    private fun setInvertedAvatar(url: String) {
        if(ImageUtils.hasImage(imgAvatar)) {
            runBlocking {
                val bitmap = ImageUtils.getCoilBitmap(scope, view.context, url)
                val invertedBitmap = bitmap?.let { ImageUtils.invertImage(it) }
                imgAvatar.setImageBitmap(invertedBitmap)
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): MembersPagingViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_members, parent, false)
            return MembersPagingViewHolder(view)
        }
    }
}