package com.l2hyunwoo.android.data.model

import com.google.gson.annotations.SerializedName

data class RequestSignUp(
    @SerializedName("birth")
    val birth: String = "1997-04-12",
    @SerializedName("email")
    val email: String,
    @SerializedName("nickname")
    val nickname: String = "Nunu",
    @SerializedName("password")
    val password: String,
    @SerializedName("phone")
    val phone: String = "010-4642-4772",
    @SerializedName("sex")
    val sex: String = "0"
)
