package com.github.members.directory.features.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.members.directory.data.State
import com.github.members.directory.data.vo.Profiles
import com.github.members.directory.data.vo.SearchProfile
import kotlinx.coroutines.launch

class HistoryViewModel(
        private val integrator: HistoryRepository
) : ViewModel() {

    private val listProfile = MutableLiveData<State<List<SearchProfile>>>()

    val stateProfile: LiveData<State<List<SearchProfile>>> get() = listProfile

    fun getGithubTopUsers() {
        viewModelScope.launch {
            val data = integrator.getTopUserProfile(USER, REF, FOLLOWERS, TYPES)
            val dataPack = State.Data(data.items)
            listProfile.postValue(dataPack)
        }
    }

    companion object {
        private const val USER = "followers:>=1000"
        private const val REF = "searchresults"
        private const val FOLLOWERS = "followers"
        private const val TYPES = "Users"
    }
}