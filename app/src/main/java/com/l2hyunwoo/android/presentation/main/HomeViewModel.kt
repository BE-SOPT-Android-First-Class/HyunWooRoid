package com.l2hyunwoo.android.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.l2hyunwoo.android.domain.entity.GithubRepoInfo
import com.l2hyunwoo.android.domain.repository.UserReposRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val userReposRepository: UserReposRepository
) : ViewModel() {
    private val _profileUrl = MutableLiveData(PROFILE_URL)
    val profileUrl: LiveData<String>
        get() = _profileUrl
    private val _userRepos = MutableLiveData<List<GithubRepoInfo>>()
    val userRepos: LiveData<List<GithubRepoInfo>>
        get() = _userRepos

    fun getUserRepos(githubId: String) {
        viewModelScope.launch {
            _userRepos.value = userReposRepository.getUserRepos(githubId)
        }
    }

    companion object {
        const val PROFILE_URL = "https://avatars.githubusercontent.com/u/54518925?v=4"
    }
}
