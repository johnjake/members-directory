package com.github.members.directory.features.details

import com.github.members.directory.data.vo.Members
import com.github.members.directory.data.vo.Profiles

interface DataSource {
    suspend fun getGithubProfile(userName: String): Profiles
    suspend fun getUserFollowers(userName: String): List<Members>
}