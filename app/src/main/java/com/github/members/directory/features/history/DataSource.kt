package com.github.members.directory.features.history

import com.github.members.directory.data.vo.SearchData

interface DataSource {
    suspend fun getTopUserProfile(
            userName: String,
            reference: String,
            followers: String,
            type: String
    ): SearchData
}