package com.l2hyunwoo.android.data.datasource

import com.l2hyunwoo.android.data.model.ResponseGithubRepository

interface GithubDataSource {
    suspend fun fetchRepoList(githubId: String): List<ResponseGithubRepository>
}