package com.l2hyunwoo.android.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.l2hyunwoo.android.domain.repository.UserReposRepository
import com.l2hyunwoo.android.presentation.main.subscreen.UIModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val userReposRepository: UserReposRepository
) : ViewModel() {
    private val _profileUrl = MutableLiveData(PROFILE_URL)
    val profileUrl: LiveData<String>
        get() = _profileUrl
    private val _userRepos = MutableLiveData<MutableList<UIModel>>()
    val userRepos: LiveData<MutableList<UIModel>>
        get() = _userRepos

    fun getUserRepos(githubId: String) {
        viewModelScope.launch {
            val repoList = userReposRepository.getUserRepos(githubId)
                .asSequence()
                .map { UIModel.Repository(it) }
                .toMutableList<UIModel>()
                .also { it.add(0, UIModel.Header) }
            _userRepos.value = repoList
        }
    }

    companion object {
        const val PROFILE_URL = "https://avatars.githubusercontent.com/u/54518925?v=4"
    }
}
