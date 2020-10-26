package com.github.members.directory.features.details

import android.content.Context
import com.github.members.baseplate_persistence.model.DBProfiles
import com.github.members.directory.data.vo.Members
import com.github.members.directory.data.vo.Profiles

interface DataSource {
    suspend fun getDbProfile(userName: String): DBProfiles
    suspend fun setDbProfile(db: DBProfiles)
    suspend fun getGithubProfile(userName: String): Profiles
    suspend fun getUserFollowers(userName: String): List<Members>
    fun checkIsLocal(context: Context): Boolean
}