package com.l2hyunwoo.android.domain.repository

import com.l2hyunwoo.android.domain.entity.UserInfo

interface SignUpRepository {
    suspend fun signUp(userInfo: UserInfo)
}
