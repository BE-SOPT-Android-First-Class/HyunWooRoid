package com.l2hyunwoo.android.domain.entity

import com.l2hyunwoo.android.presentation.main.subscreen.UIModel

data class GithubRepoInfo(
    val title: String,
    val description: String,
    val starCount: Int
) {
    fun toUIModel(): UIModel.Repository = UIModel.Repository(this)
}
