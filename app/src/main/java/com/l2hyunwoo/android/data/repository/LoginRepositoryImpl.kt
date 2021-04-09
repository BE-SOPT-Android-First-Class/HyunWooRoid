package com.l2hyunwoo.android.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.l2hyunwoo.android.data.BaseResponse
import com.l2hyunwoo.android.data.util.KEY_USER_ID
import com.l2hyunwoo.android.data.util.KEY_USER_PASSWORD
import com.l2hyunwoo.android.data.util.get
import com.l2hyunwoo.android.domain.entity.UserInfo
import com.l2hyunwoo.android.domain.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : LoginRepository {
    override suspend fun login(userInfo: UserInfo): BaseResponse<String> =
        withContext(Dispatchers.IO) {
            if (!isIdExist()) {
                return@withContext BaseResponse<String>(
                    data = "FAILURE",
                    message = "Id doesn't exist",
                    status = 400,
                    success = false
                )
            }
            if (!isValidUser(userInfo)) {
                return@withContext BaseResponse<String>(
                    data = "FAILURE",
                    message = "회원정보가 일치하지 않습니다.",
                    status = 400,
                    success = false
                )
            }
            return@withContext BaseResponse<String>(
                data = "SUCCESS",
                message = "로그인 성공",
                status = 200,
                success = true
            )
        }

    private suspend fun isIdExist() = (dataStore[KEY_USER_ID].first() != null)

    private suspend fun isValidUser(userInfo: UserInfo): Boolean {
        return ((dataStore[KEY_USER_ID].first() == userInfo.id) && (dataStore[KEY_USER_PASSWORD].first() == userInfo.password))
    }

    companion object {
        const val NOT_EXIST = "ERROR"
    }
}
