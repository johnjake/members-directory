package com.github.members.directory.features.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.members.directory.data.State
import com.github.members.directory.data.vo.Members
import com.github.members.directory.data.vo.Profiles
import kotlinx.coroutines.launch

class DetailsViewModel(
        private val integrator: DetailsRepository
) : ViewModel() {

    private val mutableList = MutableLiveData<State<List<Members>>>()
    val stateList: LiveData<State<List<Members>>> get() = mutableList

    private val profileMutable = MutableLiveData<State<Profiles>>()
    val stateProfiles: LiveData<State<Profiles>> get() = profileMutable

    fun getProfileDetails(userName: String) {
        viewModelScope.launch {
            val data = integrator.getGithubProfile(userName)
            val dataPack = State.Data(data)
            profileMutable.postValue(dataPack)
        }
    }

    fun getFollowerList(userName: String) {
        viewModelScope.launch {
            val data = integrator.getUserFollowers(userName)
            val dataPack = State.Data(data)
            mutableList.postValue(dataPack)
        }
    }
}