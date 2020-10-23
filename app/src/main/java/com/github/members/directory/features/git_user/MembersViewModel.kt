package com.github.members.directory.features.git_user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.github.members.baseplate_persistence.model.DBMembers
import com.github.members.directory.features.git_user.repository.PagingRepositoryMembers
import kotlinx.coroutines.flow.Flow


class MembersViewModel  constructor(
    private val integrator: PagingRepositoryMembers
) : ViewModel() {

    @ExperimentalPagingApi
    fun fetchGithubMembersStream(): Flow<PagingData<DBMembers>> {
        return integrator
            .getGithubMembersDB()
            .cachedIn(viewModelScope)
    }
}