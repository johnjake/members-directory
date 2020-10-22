package com.github.members.directory.api

import com.github.members.directory.data.vo.Members
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("/users")
    suspend fun getGithubUsers(
        @Query("since") pageNumber: Int
    ) : List<Members>

}