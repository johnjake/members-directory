package com.github.members.directory.features.search

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.github.members.baseplate_persistence.AppDatabase
import com.github.members.directory.api.ApiServices
import com.github.members.directory.data.vo.SearchProfile
import kotlinx.coroutines.flow.Flow

class SearchRepository(
        private val apiServices: ApiServices,
        private val appDatabase: AppDatabase,
        private val context: Context
) : DataSource {
    override fun getSearchResultStream(query: String): Flow<PagingData<SearchProfile>> {
        return Pager(config = PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = false),
                pagingSourceFactory = { SearchPagingSource(query = query, apiServices = apiServices, appDatabase, context) }
                ).flow
    }

    companion object {
        private const val DEFAULT_PAGE_SIZE = 10
    }

}