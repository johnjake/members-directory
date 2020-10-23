package com.github.members.directory.features.users.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.github.members.baseplate_persistence.AppDatabase
import com.github.members.baseplate_persistence.model.DBMembers
import com.github.members.directory.api.ApiServices
import kotlinx.coroutines.flow.Flow


class PagingRepositoryMembers(
    private val apiServices: ApiServices,
    private val appDatabase: AppDatabase
) {

    @ExperimentalPagingApi
    fun getGithubMembersDB(pagingConfig: PagingConfig = getDefaultPageConfig()): Flow<PagingData<DBMembers>> {
        val pagingSourceFactory = { appDatabase.membersDao().getRepositoryMembers() }
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = pagingSourceFactory,
            remoteMediator = PagingMediatorMembers(apiServices = apiServices, appDatabase = appDatabase)
        ).flow
    }

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = true)
    }

    companion object {
       private const val DEFAULT_PAGE_SIZE = 10
    }
}