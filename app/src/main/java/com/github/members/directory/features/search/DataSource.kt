package com.github.members.directory.features.search

import androidx.paging.PagingData
import com.github.members.baseplate_persistence.model.DBMembers
import com.github.members.directory.data.vo.SearchProfile
import kotlinx.coroutines.flow.Flow

interface DataSource {
    fun getSearchResultStream(query: String) : Flow<PagingData<SearchProfile>>
    suspend fun getSearchFromDb(username: String): List<DBMembers>
}