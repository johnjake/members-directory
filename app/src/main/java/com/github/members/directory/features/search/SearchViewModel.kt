package com.github.members.directory.features.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.github.members.directory.data.vo.SearchProfile
import kotlinx.coroutines.flow.Flow

class SearchViewModel(
        private val integrator: SearchRepository
) : ViewModel() {
    private var currentQuery: String? = null

    private var currentFlowResult: Flow<PagingData<SearchProfile>>? = null

    fun searchUserProfile(query: String) : Flow<PagingData<SearchProfile>> {
        val lastFlowResult = currentFlowResult
        if (query == currentQuery && lastFlowResult != null) {
            return lastFlowResult
        }
        currentQuery = query
        val newFlowResult: Flow<PagingData<SearchProfile>> =
                integrator
                        .getSearchResultStream(query)
                        .cachedIn(viewModelScope)
        currentFlowResult = newFlowResult
        return newFlowResult
    }
}