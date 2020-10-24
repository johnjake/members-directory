package com.github.members.directory.api

import com.github.members.directory.data.vo.Members
import com.github.members.directory.data.vo.Profiles
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {

    @GET("/users")
    suspend fun getGithubUsers(
        @Query("since") pageNumber: Int
    ) : List<Members>

    @GET("/users/{userName}")
    suspend fun getGithubProfile(
            @Path("userName") userName: String
    ): Profiles
}