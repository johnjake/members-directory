package com.github.members.directory.features.details

import android.content.Context
import com.github.members.baseplate_persistence.AppDatabase
import com.github.members.baseplate_persistence.model.DBProfiles
import com.github.members.directory.api.ApiServices
import com.github.members.directory.data.vo.Members
import com.github.members.directory.data.vo.Profiles
import com.github.members.directory.di.providesSharedOnline

class DetailsRepository(
        private val apiServices: ApiServices,
        private val appDatabase: AppDatabase,
        private val context: Context
) : DataSource {
    override suspend fun getDbProfile(userName: String): DBProfiles = appDatabase.profileDao().getUserProfile(userName)

    override suspend fun getGithubProfile(userName: String): Profiles = apiServices.getGithubProfile(userName)

    override suspend fun getUserFollowers(userName: String): List<Members> = apiServices.getUserFollowers(userName)

    override fun checkIsLocal(context: Context): Boolean = providesSharedOnline(context) ?: false

    override suspend fun setDbProfile(db: DBProfiles) {
        appDatabase.profileDao().insertProfileDao(db)
    }



}