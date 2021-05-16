package com.l2hyunwoo.android.data.datasource

import com.l2hyunwoo.android.data.model.RequestSignUp
import com.l2hyunwoo.android.data.model.ResponseSignIn
import com.l2hyunwoo.android.data.model.ResponseSignUp

interface SoptDataSource {
    suspend fun login(loginInfo: HashMap<String, String>): ResponseSignIn
    suspend fun signUp(userInfo: RequestSignUp): ResponseSignUp
}
