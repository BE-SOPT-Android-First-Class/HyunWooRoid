package com.l2hyunwoo.android.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.l2hyunwoo.android.data.BaseResponse
import com.l2hyunwoo.android.data.api.SoptApi
import com.l2hyunwoo.android.data.util.EncryptedDataStore
import com.l2hyunwoo.android.domain.entity.UserInfo
import com.l2hyunwoo.android.domain.repository.SignUpRepository
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val encryptedDataStore: EncryptedDataStore,
    private val soptApi: SoptApi
) : SignUpRepository {
    override suspend fun signUp(userInfo: UserInfo): BaseResponse<String> {
        // with(encryptedDataStore) {
        //     set(KEY_USER_ID, userInfo.id)
        //     set(KEY_USER_PASSWORD, userInfo.password)
        // }
        // dataStore.edit {
        //     it[KEY_USER_ID] = userInfo.id
        //     it[KEY_USER_PASSWORD] = userInfo.password
        // }
        return soptApi.signUp(userInfo.toSignUpDto()).toBaseResponse()
    }
}
