package com.github.members.directory.features.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.members.baseplate_persistence.model.DBProfiles
import com.github.members.directory.data.State
import com.github.members.directory.data.mapper.ProfileMapper
import com.github.members.directory.data.vo.Members
import com.github.members.directory.data.vo.Profiles
import kotlinx.coroutines.coroutineScope
 import kotlinx.coroutines.launch

class DetailsViewModel(
        private val integrator: DetailsRepository
) : ViewModel() {
    private val mapper = ProfileMapper.getInstance()
    private val mutableList = MutableLiveData<State<List<Members>>>()
    val stateList: LiveData<State<List<Members>>> get() = mutableList

    private val profileMutable = MutableLiveData<State<Profiles>>()
    val stateProfiles: LiveData<State<Profiles>> get() = profileMutable

    private suspend fun getDbProfile(userName: String): DBProfiles = integrator.getDbProfile(userName)

    fun getProfileDetails(userName: String) {
        viewModelScope.launch {
            val profile = getDbProfile(userName)
            val ss = 0
            if (profile == null) {
                val data = integrator.getGithubProfile(userName)
                coroutineScope {
                    val dbProfile = mapper.fromData(data)
                    integrator.setDbProfile(dbProfile)
                }
                val dataPack = State.Data(data)
                profileMutable.postValue(dataPack)

            } else {
                val data = mapper.fromStorage(profile)
                val dataPack = State.Data(data)
                profileMutable.postValue(dataPack)
            }
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