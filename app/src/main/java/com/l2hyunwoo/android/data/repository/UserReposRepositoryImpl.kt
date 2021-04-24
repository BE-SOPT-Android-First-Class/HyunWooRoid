package com.l2hyunwoo.android.data.repository

import com.l2hyunwoo.android.data.datasource.GithubDataSource
import com.l2hyunwoo.android.domain.entity.GithubRepoInfo
import com.l2hyunwoo.android.domain.repository.UserReposRepository
import javax.inject.Inject

class UserReposRepositoryImpl @Inject constructor(
    private val githubDataSource: GithubDataSource
) : UserReposRepository {
    override suspend fun getUserRepos(githubId: String): List<GithubRepoInfo> {
        return githubDataSource.fetchRepoList(githubId)
            .map { it.toRepositoryInfo() }
    }
}