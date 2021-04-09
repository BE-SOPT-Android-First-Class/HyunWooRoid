package com.l2hyunwoo.android.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.l2hyunwoo.android.data.util.KEY_USER_ID
import com.l2hyunwoo.android.data.util.KEY_USER_PASSWORD
import com.l2hyunwoo.android.domain.entity.UserInfo
import com.l2hyunwoo.android.domain.repository.SignUpRepository
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : SignUpRepository {
    override suspend fun signUp(userInfo: UserInfo) {
        dataStore.edit {
            it[KEY_USER_ID] = userInfo.id
            it[KEY_USER_PASSWORD] = userInfo.password
        }
    }
}
