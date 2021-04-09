package com.l2hyunwoo.android.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// TODO 지금은 서버통신 구조를 확인할 수 없어서 Domain 데이터 구조를 확정지을 수 없음
@Parcelize
data class UserInfo(
    val id: String,
    val password: String
) : Parcelable
