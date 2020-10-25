package com.github.members.directory.features.history.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import coil.load
import com.github.members.directory.R
import com.github.members.directory.data.vo.SearchProfile

class SliderPagerAdapter(
        private val context: Context
) : PagerAdapter() {


    private var userList: List<SearchProfile> = emptyList()

    var dataSource: List<SearchProfile>
        get() = userList
        set(value) {
            userList = value
        }

    @SuppressLint("SetTextI18n")
    @NonNull
    override fun instantiateItem(@NonNull container: ViewGroup, position: Int): View {
        val profile = userList[position]
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val slideLayout: View = inflater.inflate(R.layout.item_slider_discover, null)
        val slideImg = slideLayout.findViewById<ImageView>(R.id.slide_img)
        val slideText = slideLayout.findViewById<TextView>(R.id.slide_title)
        slideImg.load(profile.avatar_url)
        slideText.text = profile.login
        container.addView(slideLayout)
        return slideLayout
    }

    override fun getCount(): Int = userList.size

    override fun isViewFromObject(view: View, objt: Any): Boolean {
        return view === objt
    }

    override fun destroyItem(container: ViewGroup, position: Int, objt: Any) {
        (container as ViewPager).removeView(objt as View)
    }
}