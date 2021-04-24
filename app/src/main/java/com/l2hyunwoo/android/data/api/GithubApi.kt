package com.l2hyunwoo.android.data.api

import com.l2hyunwoo.android.data.model.ResponseGithubRepository
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {
    @GET("users/{user}/repos")
    suspend fun getRepoList(
        @Path("user") userId: String
    ): List<ResponseGithubRepository>
}