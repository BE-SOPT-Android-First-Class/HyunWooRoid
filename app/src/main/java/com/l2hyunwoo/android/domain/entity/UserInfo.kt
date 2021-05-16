package com.l2hyunwoo.android.domain.entity

import android.os.Parcelable
import com.l2hyunwoo.android.data.model.RequestSignUp
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfo(
    val id: String,
    val password: String
) : Parcelable {
    fun toSignUpDto(): RequestSignUp {
        return RequestSignUp(
            email = id,
            password = password
        )
    }
}
