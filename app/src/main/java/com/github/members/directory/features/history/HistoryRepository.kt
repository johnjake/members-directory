package com.github.members.directory.features.history

import com.github.members.directory.api.ApiServices
import com.github.members.directory.data.vo.SearchData

class HistoryRepository(
        private val apiServices: ApiServices
) : DataSource {
    override suspend fun getTopUserProfile(userName: String, reference: String, followers: String, type: String): SearchData =
        apiServices.getTopUserProfile(userName, reference, followers, type)
}