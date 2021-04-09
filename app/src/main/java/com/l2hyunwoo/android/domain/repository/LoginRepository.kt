package com.l2hyunwoo.android.domain.repository

import com.l2hyunwoo.android.data.BaseResponse
import com.l2hyunwoo.android.domain.entity.UserInfo

interface LoginRepository {
    suspend fun login(userInfo: UserInfo): BaseResponse<String>
}
