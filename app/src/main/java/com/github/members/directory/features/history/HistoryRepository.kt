package com.github.members.directory.features.history

import com.github.members.baseplate_persistence.AppDatabase
import com.github.members.baseplate_persistence.model.DBSearch
import com.github.members.directory.api.ApiServices
import com.github.members.directory.data.vo.SearchData

class HistoryRepository(
        private val apiServices: ApiServices,
        private val appDatabase: AppDatabase
) : DataSource {
    override suspend fun getTopUserProfile(userName: String, reference: String, followers: String, type: String): SearchData =
        apiServices.getTopUserProfile(userName, reference, followers, type)

    override suspend fun getUserSearch(): List<DBSearch> = appDatabase.searchDao().getUserSearch()
}