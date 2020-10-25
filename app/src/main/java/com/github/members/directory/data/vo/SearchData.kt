package com.github.members.directory.data.vo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchData(
        val total_count : Int,
        val incomplete_results : Boolean,
        val items : List<SearchProfile>
) : Parcelable