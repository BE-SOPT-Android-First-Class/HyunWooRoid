package com.l2hyunwoo.android.data.model

import com.google.gson.annotations.SerializedName
import com.l2hyunwoo.android.data.BaseResponse

data class ResponseSignUp(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
) {
    data class Data(
        @SerializedName("nickname")
        val nickname: String
    )

    fun toBaseResponse(): BaseResponse<String> {
        return BaseResponse(
            data = data.nickname,
            status = 0,
            message = message,
            success = success
        )
    }
}
