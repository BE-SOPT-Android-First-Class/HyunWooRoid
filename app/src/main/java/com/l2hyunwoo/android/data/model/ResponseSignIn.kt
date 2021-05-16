package com.l2hyunwoo.android.data.model

import com.google.gson.annotations.SerializedName

data class ResponseSignIn(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String
)
