package com.l2hyunwoo.android.data.api

import com.l2hyunwoo.android.data.model.RequestSignUp
import com.l2hyunwoo.android.data.model.ResponseSignIn
import com.l2hyunwoo.android.data.model.ResponseSignUp
import retrofit2.http.Body
import retrofit2.http.POST

interface SoptApi {
    @POST("login/signin")
    suspend fun login(
        @Body requestLogin: HashMap<String, String>
    ): ResponseSignIn

    @POST("login/signup")
    suspend fun signUp(
        @Body requestSignUp: RequestSignUp
    ): ResponseSignUp
}
