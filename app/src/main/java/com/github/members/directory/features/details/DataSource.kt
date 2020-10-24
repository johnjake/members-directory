package com.github.members.directory.features.details

import com.github.members.directory.data.vo.Profiles

interface DataSource {
    suspend fun getGithubProfile(userName: String): Profiles
}