package com.l2hyunwoo.android.data

data class BaseResponse<T>(
    val data: T,
    val message: String,
    val status: Int,
    val success: Boolean
)
