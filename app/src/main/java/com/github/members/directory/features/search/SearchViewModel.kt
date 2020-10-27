package com.github.members.directory.features.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.github.members.directory.data.State
import com.github.members.directory.data.mapper.MembersMapper
import com.github.members.directory.data.vo.Members
import com.github.members.directory.data.vo.SearchProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SearchViewModel(
        private val integrator: SearchRepository
) : ViewModel() {

    private val mapper = MembersMapper.getInstance()
    private val dbProfile = MutableLiveData<State<List<Members>>>()
    val stateProfile: LiveData<State<List<Members>>> get() = dbProfile

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

    fun searchFromDb(query: String) {
        viewModelScope.launch {
            val mutableList: MutableList<Members> = arrayListOf()
            val data = integrator.getSearchFromDb(query)
            data.forEach { user ->
                val profile = mapper.fromStorage(user)
                mutableList.add(profile)
            }
            if(mutableList.isNotEmpty()) {
                val dataPack = State.Data(mutableList)
                dbProfile.postValue(dataPack)
            }
        }
    }
}