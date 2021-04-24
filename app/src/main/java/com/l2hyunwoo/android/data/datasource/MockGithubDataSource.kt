package com.l2hyunwoo.android.data.datasource

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.l2hyunwoo.android.data.model.ResponseGithubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.reflect.Type
import javax.inject.Inject

class MockGithubDataSource @Inject constructor(
    private val context: Context
) : GithubDataSource {
    override suspend fun fetchRepoList(githubId: String): List<ResponseGithubRepository> {
        return withContext(Dispatchers.IO) {
            val repoListJsonFile = runCatching {
                context.assets
                    .open("fake_repo_list.json")
                    .bufferedReader()
                    .use { it.readText() }
            }
            Gson().fromJson(repoListJsonFile.getOrNull(), typeOf<List<ResponseGithubRepository>>())
        }
    }
}

inline fun <reified T> typeOf(): Type = object : TypeToken<T>() {}.type