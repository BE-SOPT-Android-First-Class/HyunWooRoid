package com.l2hyunwoo.android.data.model

import com.google.gson.annotations.SerializedName
import com.l2hyunwoo.android.data.BaseResponse

data class ResponseSignIn(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String
) {
    fun toBaseResponse(): BaseResponse<String> {
        return BaseResponse(
            data = "",
            success = success,
            message = message,
            status = 0
        )
    }
}
