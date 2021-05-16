package com.l2hyunwoo.android.data.util

import javax.inject.Inject

interface UrlStore {
    fun getSoptBaseUrl(): String
    fun getGithubBaseUrl(): String
}

class UrlStoreImpl @Inject constructor() : UrlStore {
    init {
        System.loadLibrary("native")
    }

    override fun getSoptBaseUrl(): String {
        return getConstant("SOPT_BASE_URL")
    }

    override fun getGithubBaseUrl(): String {
        return getConstant("GITHUB_BASE_URL")
    }

    private external fun getConstant(key: String): String
}
