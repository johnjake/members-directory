package com.github.members.directory.features.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.members.baseplate_persistence.model.DBSearch
import com.github.members.directory.data.State
import com.github.members.directory.data.mapper.SearchMapper
import com.github.members.directory.data.vo.Profiles
import com.github.members.directory.data.vo.SearchProfile
import kotlinx.coroutines.launch

class HistoryViewModel(
        private val integrator: HistoryRepository
) : ViewModel() {
    private val mapper = SearchMapper.getInstance()

    private val searchData: MutableList<SearchProfile> = arrayListOf()

    private val listSearch = MutableLiveData<State<List<SearchProfile>>>()

    val stateSearch: LiveData<State<List<SearchProfile>>> get() = listSearch

    private val listProfile = MutableLiveData<State<List<SearchProfile>>>()

    val stateProfile: LiveData<State<List<SearchProfile>>> get() = listProfile

    fun getGithubTopUsers() {
        viewModelScope.launch {
            val data = integrator.getTopUserProfile(USER, REF, FOLLOWERS, TYPES)
            val dataPack = State.Data(data.items)
            listProfile.postValue(dataPack)
        }
    }

    fun getUserSearchList() {
        viewModelScope.launch {
            val data = integrator.getUserSearch()
            data.forEach { map ->
                val mapData = mapper.fromStorage(map)
                searchData.add(mapData)
            }
            val dataPack = State.Data(searchData)
            listSearch.postValue(dataPack)
        }
    }

    companion object {
        private const val USER = "followers:>=1000"
        private const val REF = "searchresults"
        private const val FOLLOWERS = "followers"
        private const val TYPES = "Users"
    }
}