package com.l2hyunwoo.android.domain.repository

import com.l2hyunwoo.android.domain.entity.GithubRepoInfo

interface UserReposRepository {
    suspend fun getUserRepos(githubId: String): List<GithubRepoInfo>
}