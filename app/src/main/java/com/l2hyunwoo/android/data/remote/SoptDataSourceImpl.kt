package com.l2hyunwoo.android.data.remote

import com.l2hyunwoo.android.data.api.SoptApi
import com.l2hyunwoo.android.data.datasource.SoptDataSource
import com.l2hyunwoo.android.data.model.RequestSignUp
import com.l2hyunwoo.android.data.model.ResponseSignIn
import com.l2hyunwoo.android.data.model.ResponseSignUp
import javax.inject.Inject

class SoptDataSourceImpl @Inject constructor(
    private val soptApi: SoptApi
) : SoptDataSource {
    override suspend fun login(loginInfo: HashMap<String, String>): ResponseSignIn {
        return soptApi.login(loginInfo)
    }

    override suspend fun signUp(userInfo: RequestSignUp): ResponseSignUp {
        return soptApi.signUp(userInfo)
    }
}
