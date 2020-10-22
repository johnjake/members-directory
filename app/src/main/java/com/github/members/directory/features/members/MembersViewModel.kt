package com.github.members.directory.features.members

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.github.members.baseplate_persistence.model.DBMembers
import com.github.members.directory.features.members.repository.PagingRepositoryMembers
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
class MembersViewModel  constructor(
    private val integrator: PagingRepositoryMembers
) : ViewModel() {

    fun fetchGithubMembersStream(): Flow<PagingData<DBMembers>> {
        return integrator
            .getGithubMembersDB()
            .cachedIn(viewModelScope)
    }

}