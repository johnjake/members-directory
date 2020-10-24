package com.github.members.directory.features.details

import com.github.members.baseplate_persistence.AppDatabase
import com.github.members.directory.api.ApiServices
import com.github.members.directory.data.vo.Profiles

class DetailsRepository(
        private val apiServices: ApiServices,
        private val appDatabase: AppDatabase
) : DataSource {
    override suspend fun getGithubProfile(userName: String): Profiles = apiServices.getGithubProfile(userName)
}