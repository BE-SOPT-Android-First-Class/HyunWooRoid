package com.l2hyunwoo.android.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    private val _profileUrl = MutableLiveData(PROFILE_URL)
    val profileUrl: LiveData<String>
        get() = _profileUrl

    companion object {
        const val PROFILE_URL = "https://avatars.githubusercontent.com/u/54518925?v=4"
    }
}
