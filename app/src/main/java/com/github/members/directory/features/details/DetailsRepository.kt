package com.github.members.directory.features.details

import com.github.members.baseplate_persistence.AppDatabase
import com.github.members.baseplate_persistence.model.DBProfiles
import com.github.members.directory.api.ApiServices
import com.github.members.directory.data.vo.Members
import com.github.members.directory.data.vo.Profiles

class DetailsRepository(
        private val apiServices: ApiServices,
        private val appDatabase: AppDatabase
) : DataSource {
    override suspend fun getDbProfile(userName: String): DBProfiles = appDatabase.profileDao().getUserProfile(userName)
    override suspend fun setDbProfile(db: DBProfiles) {
        appDatabase.profileDao().insertProfileDao(db)
    }

    override suspend fun getGithubProfile(userName: String): Profiles = apiServices.getGithubProfile(userName)
    override suspend fun getUserFollowers(userName: String): List<Members> = apiServices.getUserFollowers(userName)
}